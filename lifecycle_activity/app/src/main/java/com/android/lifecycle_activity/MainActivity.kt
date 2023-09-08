package com.android.lifecycle_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var btnNavigation: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNavigation = findViewById(R.id.navigation)

        btnNavigation?.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)
        }
        Log.d("aaa", " onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("aaa", " onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("aaa", " onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("aaa", " onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("aaa", " onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("aaa", " onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("aaa", " onDestroy")
    }
}