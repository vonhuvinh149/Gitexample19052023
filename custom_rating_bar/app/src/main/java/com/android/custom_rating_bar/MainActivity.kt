package com.android.custom_rating_bar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar

class MainActivity : AppCompatActivity() {

    private var rb : RatingBar? = null ;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rb = findViewById(R.id.ratingBar1)

    }

    override fun onStart() {
        super.onStart()
    }
}