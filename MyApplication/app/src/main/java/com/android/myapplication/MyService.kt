package com.android.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

    private var CHANNEL_ID = "my_channel"
    private var notification: Notification? = null
    private lateinit var notificationManager: NotificationManager
    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notification = createNotification(this, notificationManager)
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            repeat(10000000000.toInt()) {
                delay(100)
            }
            Log.d("BBB", "finish")
        }
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun createNotification(
        context: Context, notificationManager: NotificationManager
    ): Notification {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        notification.setContentTitle("thông báo")
        notification.setContentText("App có phiên bản mới")
        notification.priority = NotificationCompat.PRIORITY_DEFAULT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, "Version new ", NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return notification.build()
    }

}