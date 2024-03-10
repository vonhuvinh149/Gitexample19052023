package com.android.my_app_music.data.repository


import com.android.my_app_music.data.model.Song
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

}