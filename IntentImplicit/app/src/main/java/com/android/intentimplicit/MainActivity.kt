package com.android.intentimplicit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var tvScore: TextView? = null
    private var imgRandom: ImageView? = null
    private var imgUserSelect: ImageView? = null
    private var REQUEST_CODE = 1
    private var imageDrawableResource: Int = -1


    private var listImagesName: List<String> = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvScore = findViewById(R.id.text_view_score)
        imgRandom = findViewById(R.id.image_view_random)
        imgUserSelect = findViewById(R.id.image_view_pick)

        // lấy ra string array từ resource
        listImagesName = resources.getStringArray(R.array.array_images).toList()

        randomImage()

        imgUserSelect?.setOnClickListener {
            val intent = Intent(this@MainActivity, ListAnimalActivity::class.java)
            intent.putExtra("list_image", listImagesName.toTypedArray())
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val resourceId = data.getIntExtra("image_resource_id", -1)
            if (resourceId != -1) {
                imgUserSelect?.setImageResource(resourceId)
                if (resourceId == imageDrawableResource) {
                    randomImage()
                    imgUserSelect?.setImageResource(R.drawable.no_photo)
                }
            }
        }
    }

    private fun randomImage() {
        val random = Random.nextInt(listImagesName.size)
        var firstImageName = listImagesName[random]
        // chuyển  đổi từ tên sang file drawable
        imageDrawableResource = resources.getIdentifier(firstImageName, "drawable", packageName)
        imgRandom?.setImageResource(imageDrawableResource)
    }
}