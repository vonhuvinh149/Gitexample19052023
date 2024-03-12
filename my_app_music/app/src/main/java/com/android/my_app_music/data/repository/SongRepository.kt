package com.android.my_app_music.data.repository


import android.util.Log
import com.android.my_app_music.data.model.Song
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import java.text.Normalizer
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
                        val item = e.getValue(Song::class.java)
                        val songName = removeDiacritics(item?.songName ?: "")
                        val singerName: String =
                            removeDiacritics(item?.singerName ?: "")
                        val str = removeDiacritics(searchValue)
                        if (singerName.contains(str, ignoreCase = true) || songName.contains(str , ignoreCase = true)) {
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

    private fun removeDiacritics(input: String): String {
        val normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(normalized).replaceAll("")
    }

}