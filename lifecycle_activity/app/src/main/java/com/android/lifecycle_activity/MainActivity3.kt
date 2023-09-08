package com.android.lifecycle_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity3 : AppCompatActivity() {

    private var backMainBtn : Button? = null
    private var thirdBtn : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        backMainBtn = findViewById(R.id.mainBtn)
        thirdBtn = findViewById(R.id.thirdBtn)

        Log.d("aaa", "3 onCreate")

        backMainBtn?.setOnClickListener {
            val intent = Intent(this@MainActivity3 , MainActivity::class.java)
            startActivity(intent)
        }

        thirdBtn?.setOnClickListener {
            val intent = Intent(this@MainActivity3 , MainActivity3::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("aaa", "3 onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("aaa", "3 onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("aaa", "3 onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("aaa", "3 onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("aaa", "3 onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("aaa", "3 onDestroy")
    }
}