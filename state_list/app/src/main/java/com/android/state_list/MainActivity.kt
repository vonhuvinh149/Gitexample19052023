package com.android.state_list

import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private var btnClip: Button? = null

    //    private var imgClip: ImageView? = null
    private var img1: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
// main 2
//        btnClip = findViewById(R.id.btn)
//        imgClip = findViewById(R.id.img)
//
//        btnClip?.setOnClickListener {
//            val drawable: ClipDrawable? = imgClip?.drawable as ClipDrawable
//            if (drawable != null) {
//                drawable.level += 1000
//            }
//        }

        // main 3
        img1 = findViewById(R.id.img1)
        btnClip = findViewById(R.id.btnClip)

        btnClip?.setOnClickListener {
//            img1?.setImageResource(R.drawable.clip_drawable_stars)
            val drawable1: ClipDrawable? = img1?.drawable as? ClipDrawable
            if (drawable1 != null) {
                drawable1.level += 1000
            }
        }
    }
}