package com.android.service_android

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private val CHANNEL_ID = "my_channel_id"
    private var notification: Notification? = null
    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        Log.d("BBB", notification.toString())
        notification = createNotification(this@MyService, notificationManager)
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "destroy ", Toast.LENGTH_SHORT).show()
    }

    private fun createNotification(
        context: Context,
        notificationManager: NotificationManager
    ): Notification {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        notification.setContentTitle("Thong Bao")
            .setContentText("App co phien ban moi")
            .priority = NotificationCompat.PRIORITY_HIGH

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "New Version",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return notification.build()
    }

}