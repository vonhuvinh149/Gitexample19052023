package com.android.myservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private var btnStart: Button? = null;
    private var btnStop: Button? = null;
    private var textCount: TextView? = null
    private lateinit var myService: MyService

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

    override fun onStart() {
        super.onStart()
        Intent(this, MyService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        Intent(this@MainActivity, MyService::class.java).also { intent ->
            unbindService(connection)
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, iBinder: IBinder?) {
            myService = (iBinder as MyService.MyBinder).getService()
            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                override fun run() {
                    textCount?.text = "Count: ${myService.getCount()}"
                    Handler(Looper.getMainLooper()).postDelayed(this, 1000)
                }

            }, 1000)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }


    private fun mapping() {
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)
        textCount = findViewById(R.id.text_count)
    }

}