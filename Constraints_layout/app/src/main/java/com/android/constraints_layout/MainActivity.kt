package com.android.constraints_layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var image: ImageView;
    private lateinit var btn1: Button;
    private lateinit var zoom: Button;
    private lateinit var opacity: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        image = findViewById(R.id.image)
        btn1 = findViewById(R.id.btn1)
        zoom = findViewById(R.id.zoom)
        opacity = findViewById(R.id.opacity)

        btn1.setOnClickListener {
            image.setImageResource(R.drawable.img_1)
            image.setScaleType(ImageView.ScaleType.FIT_CENTER)
            image.setAlpha(0)
        }

        zoom.setOnClickListener {
            image.setScaleType(ImageView.ScaleType.CENTER_CROP)
        }

        opacity.setOnClickListener {
            image.setAlpha(50)
        }

    }
}