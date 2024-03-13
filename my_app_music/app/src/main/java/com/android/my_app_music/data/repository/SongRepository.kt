package com.android.my_app_music.data.repository


import android.util.Log
import com.android.my_app_music.data.model.Song
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.Collator
import java.text.Normalizer
import java.util.Locale
import java.util.regex.Pattern

class SongRepository {

    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("music")

    fun fetchListSongDataFromFirebase(callback: (MutableList<Song>) -> Unit) {
        dbRef.child("song").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var listSongs: MutableList<Song> = mutableListOf()
                if (snapshot.exists()) {
                    for (e in snapshot.children) {
                        val song = e.getValue(Song::class.java)
                        song?.let { listSongs.add(it) }
                    }
                    callback.invoke(listSongs)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(mutableListOf())
            }
        })
    }

    fun fetchSearch(searchValue: String, callback: (MutableList<Song>) -> Unit) {
        var listSongs: MutableList<Song> = mutableListOf()
        dbRef.child("song").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (e in snapshot.children) {
                        var item = e.getValue(Song::class.java)
                        var songName = convertString(item?.songName ?: "")
                        var singerName = convertString(item?.singerName ?: "")
                        var str = convertString(searchValue)

                        songName = removeDiacritics(songName)
                        singerName = removeDiacritics(singerName)
                        str = removeDiacritics(str)
                        if (singerName.contains(str, ignoreCase = true) ||
                            songName.contains(str, ignoreCase = true)
                        ) {
                            item?.let { listSongs.add(it) }
                        }
                    }
                    callback.invoke(listSongs)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(mutableListOf())
            }
        })
    }

    private fun removeDiacritics(str: String): String {
        val normalized = Normalizer.normalize(str, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(normalized).replaceAll("")
    }

    private fun convertString(str: String): String {
        var newStr = str.lowercase().replace("đ", "d")
        newStr = newStr.lowercase().replace("ư", "u")
        newStr = newStr.lowercase().replace("ơ", "o")
        newStr = newStr.lowercase().replace("ô", "o")
        return newStr
    }
}