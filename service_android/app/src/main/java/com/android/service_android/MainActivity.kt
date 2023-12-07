package com.android.service_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var btnStart: Button? = null
    private var btnStop: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        event()
    }

    private fun event() {
        btnStart?.setOnClickListener {
            val service = Intent(this@MainActivity, MyService::class.java)
            service.putExtra("test_key" , "hello")
            startService(service)
        }

        btnStop?.setOnClickListener {
            val service = Intent(this@MainActivity , MyService::class.java)
            stopService(service)
        }
    }

    private fun initView() {
        btnStart = findViewById(R.id.start)
        btnStop = findViewById(R.id.stop)
    }
}