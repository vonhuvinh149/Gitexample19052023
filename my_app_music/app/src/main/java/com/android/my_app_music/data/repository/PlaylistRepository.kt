package com.android.my_app_music.data.repository

import com.android.my_app_music.data.model.Playlist
import com.android.my_app_music.data.model.Song
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlaylistRepository {

    private val dbRef = FirebaseDatabase.getInstance().getReference("music")

    fun getPlaylistFromFirebase(callback: (MutableList<Playlist>) -> Unit) {
        var lists: MutableList<Playlist> = mutableListOf()
        dbRef.child("playlist").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (e in snapshot.children) {
                        val item = e.getValue(Playlist::class.java)
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

    fun getListSongPlaylist(playlistId: Int, callback: (MutableList<Song>) -> Unit) {
        var listSongs: MutableList<Song> = mutableListOf()

        dbRef.child("song").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (e in snapshot.children) {
                        val playlistSnapshot = e.child("playlist")
                        if (playlistSnapshot.exists()) {
                            val snapId = playlistSnapshot.getValue(Int::class.java) ?: 0
                            if (snapId == playlistId) {
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