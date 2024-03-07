package com.android.my_app_music

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.my_app_music.data.model.Album
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.data.service.SongService
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class TestActivity : AppCompatActivity() {

    private lateinit var dbRef: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val database = Firebase.database
        val myRef = database.getReference("music")


        val album1 = Album(
            1,
            "Note Buồn Trên Cát",
            "Khói",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2F1_notebuontrencat.jpg?alt=media&token=e20ca6e5-bca2-45f8-a797-c95d41ac7f2b"
        )

        val album2 = Album(
            2,
            "Loser2Lover",
            "Bray",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2F2_Loser2Lover.jpg?alt=media&token=53375617-89e4-4ab4-a988-389bd1056bdc"
        )

        val album3 = Album(
            3,
            "Đan Xinh In Love",
            "Binz",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2F3_danxininlove.jpg?alt=media&token=a5031e2c-d41c-4bd0-b38e-0f3e6eac047a"
        )

        val album4 = Album(
            4,
            "KoBuKoVu",
            "Đen Vâu",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2F4_kobukovu.jpg?alt=media&token=3a002cf4-f204-4c1c-990e-0c3ce55ce668"
        )

        val album5 = Album(
            5 ,
            "Đĩa Than" ,
            "Táo",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2F5_diathantao.jpg?alt=media&token=2a750bb1-2932-4a12-874b-104d3065bc27"
        )

        val album6 = Album(
            6 ,
            "DRUNK ON MUSIC",
        "Đạt G",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2F6_drunonmusic.jpg?alt=media&token=fa846b52-0406-4e8f-85d1-fd101d04c3fb"
        )

        val album7 = Album(
            7 ,
            "Không Tựa" ,
            "Karik" ,
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2F7_khongtua.jpg?alt=media&token=7519dc72-2e72-43ef-915d-c288f2c742e9"
        )

        myRef.child("albums").push().setValue(album1)
        myRef.child("albums").push().setValue(album2)
        myRef.child("albums").push().setValue(album3)
        myRef.child("albums").push().setValue(album4)
        myRef.child("albums").push().setValue(album5)
        myRef.child("albums").push().setValue(album6)
        myRef.child("albums").push().setValue(album7)
    }
}