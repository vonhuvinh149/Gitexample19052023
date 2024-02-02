package com.android.intent_kotlin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var btnTest: Button? = null
    var btnGoToSecond: Button? = null
    var btnGoToThree: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTest = findViewById(R.id.btn_navigation)
        btnGoToSecond = findViewById(R.id.btn_navigation_second)
        btnGoToThree = findViewById(R.id.btn_navigation_three)

        btnTest?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://tuhoc.cc"))
            startActivity(intent)
        }

        btnGoToThree?.setOnClickListener {
            val intent = Intent(this, ThreeActivity::class.java)
            val bundle = Bundle()

            bundle.putString("name", "vo nhu vinh")
            bundle.putInt("age", 26)
            bundle.putDouble("weight", 65.0)

            intent.putExtras(bundle)
            startActivity(intent)
        }

        btnGoToSecond?.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("name", "vo nhu vinh")
            startActivity(intent)
        }
    }
}