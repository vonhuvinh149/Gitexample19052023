package com.android.android_extensions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton

class ImageActivity : AppCompatActivity() {

    var imgTest: ImageView? = null
    var radLastImage: RadioButton? = null
    var radFirstImage: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        initView()
        event()
    }

    private fun event() {
        radLastImage?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                imgTest?.setImageResource(R.drawable.img)
            }
        }

        radFirstImage?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                imgTest?.setImageResource(R.drawable.img_1)
            }
        }
    }

    private fun initView() {
        imgTest = findViewById(R.id.img_test)
        radLastImage = findViewById(R.id.rad_image_1)
        radFirstImage = findViewById(R.id.rad_image_2)
    }
}