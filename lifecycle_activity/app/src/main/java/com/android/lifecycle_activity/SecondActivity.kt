package com.android.lifecycle_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class SecondActivity : AppCompatActivity() {

    private var btnScreen: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        btnScreen = findViewById(R.id.btnScreen3)

        btnScreen?.setOnClickListener {
            val intent = Intent(this@SecondActivity, MainActivity3::class.java)
            startActivity(intent)
        }

        Log.d("aaa", "sc onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("aaa", "sc onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("aaa", "sc onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("aaa", "sc onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("aaa", "sc onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("aaa", "sc onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("aaa", "sc onDestroy")
    }
}