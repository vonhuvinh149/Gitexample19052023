package com.android.my_app_music.data.repository

import android.util.Log
import com.android.my_app_music.data.model.Album
import com.android.my_app_music.data.model.Song
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AlbumRepository {

    private val dbRef = FirebaseDatabase.getInstance().getReference("music")

    fun getAlbumFromFirebase(callback: (MutableList<Album>) -> Unit) {
        var lists: MutableList<Album> = mutableListOf()
        dbRef.child("albums").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (e in snapshot.children) {
                        val item = e.getValue(Album::class.java)
                        item?.let { lists.add(it) }
                    }
                    callback.invoke(lists)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(mutableListOf())
            }
        })
    }

    fun getListSongAlbum(albumId: Int, callback: (MutableList<Song>) -> Unit) {
        Log.d("BBB" , "id $albumId")
        var listSongs: MutableList<Song> = mutableListOf()
        dbRef.child("song").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (e in snapshot.children) {
                        val albumSnapshot = e.child("album")
                        if (albumSnapshot.exists()) {
                            val snapId = albumSnapshot.getValue(Int::class.java) ?: 0
                            if (snapId == albumId) {
                                val item = e.getValue(Song::class.java)
                                item?.let { listSongs.add(it) }
                            }
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
}