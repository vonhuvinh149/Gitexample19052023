package com.android.myservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var btnStart: Button? = null;
    private var btnStop: Button? = null;
    private var textCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapping()


        val messageFromService = intent.getStringExtra(AppConstant.NOTIFICATION_KEY)
        if (messageFromService?.isNotEmpty() == true) {
            when (messageFromService) {
                AppConstant.NOTIFICATION_OPEN -> {
                    Toast.makeText(this, "Open from service", Toast.LENGTH_SHORT).show()
                }

                AppConstant.NOTIFICATION_START -> {
                    val intent = Intent(this@MainActivity, MyService::class.java)
                    intent.putExtra(
                        AppConstant.ACTIVITY_START_SERVICE_KEY,
                        AppConstant.START_COUNT_SERVICE
                    )
                    startService(intent)
                }
            }
        }

        btnStart?.setOnClickListener {
            val intent = Intent(this@MainActivity, MyService::class.java)
            startService(intent)
        }
    }

    private fun mapping() {
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)
        textCount = findViewById(R.id.btn_start)
    }

}