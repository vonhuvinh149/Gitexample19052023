package com.android.navigates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
//        val intent: Intent = getIntent()
//        val value: String? = intent.getStringExtra("aaa")
//        Log.d("zzz" , value.toString())
    }
}