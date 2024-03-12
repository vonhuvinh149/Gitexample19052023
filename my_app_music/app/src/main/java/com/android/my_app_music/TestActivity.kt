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

        val song = Song(
            22,
            "Ngày lang thang",
            "Đen , JGKID , Dr.Quang",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2Fngaylangthan.jpg?alt=media&token=6e9a5e5d-a10b-4021-a4aa-b45f1c511860",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/music%2FNgay-Lang-Thang-Den-ft-JGKiD-ft-Dr-Quang.mp3?alt=media&token=fa272f02-ac7b-4c69-8eeb-1ea51672a8f6",
            4,
            3,
            0,
            false
        )

        val song1 = Song(
            23,
            "Lối nhỏ",
            "Đen , Phương Anh Đào",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2Floinho.jpg?alt=media&token=70e57d8c-edde-4732-a272-b033e436c0c8",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/music%2FLoi-Nho-Den-Phuong-Anh-Dao.mp3?alt=media&token=662ee4d8-4aca-4d03-aedf-7749ba35c0bc",
            4,
            2,
            0,
            false
        )

        val song2 = Song(
            24,
            "Gieo Quẻ",
            "Đen , Hoàng Thuỳ Linh",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2Fgieoque.jpg?alt=media&token=352f9828-f674-49fb-9334-1209769a9a4b",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/music%2FGieoQue-HoangThuyLinhFeatDen-7702264.mp3?alt=media&token=3802a120-6358-468e-9e50-f88f0c90d8eb",
            4,
            1,
            0,
            false
        )

        val song4 = Song(
            25,
            "Luôn yêu đời",
            "Đen , Cheng",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2Fluonyeudoi.jpg?alt=media&token=edf755c9-4bde-49f0-b1f4-68922fdfb33d",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/music%2FLuonYeuDoi-Den-8924307.mp3?alt=media&token=6dd9a78e-ac41-4584-a233-d9879f74df52",
            4,
            3,
            0,
            false
        )

        val song3 = Song(
            26,
            "Ngày khác lạ",
            "Đen , Giang Phạm , TripleD",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2Fngaykhacla.jpg?alt=media&token=a4212f4d-c265-4a7a-967b-b580db311b12",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/music%2FNgayKhacLa-DenDJGiangPhamTripleD-5393909.mp3?alt=media&token=22f3fc13-0027-4b8b-8419-7e96330db225",
            4,
            2,
            0,
            false
        )

        val song5 = Song(
            27,
            "Nhạc của rừng",
            "Đen , Hiền VK",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/image%2Fnhaccuarung.jpg?alt=media&token=37fe051e-3c9a-446c-90ac-31386e64ffda",
            "https://firebasestorage.googleapis.com/v0/b/db-app-music.appspot.com/o/music%2FNhacCuaRung-DenHienVK-13940254.mp3?alt=media&token=1764102b-216a-4029-8213-ee8b78bb628b",
            4,
            1,
            0,
            false
        )

        myRef.child("song").push().setValue(song)
        myRef.child("song").push().setValue(song1)
        myRef.child("song").push().setValue(song2)
        myRef.child("song").push().setValue(song3)
        myRef.child("song").push().setValue(song4)
        myRef.child("song").push().setValue(song5)
    }
}