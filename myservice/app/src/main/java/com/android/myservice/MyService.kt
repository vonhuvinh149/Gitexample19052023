package com.android.myservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyService : Service() {

    private val CHANNEL_ID = "my channel"
    private var notification: Notification? = null
    private lateinit var notificationManager: NotificationManager
    private var isStart = false
    private var count = 0
    private var binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("BBB", "onCreate")
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notification = this.createNotification(
            this@MyService,
            "Thong bao",
            "notification da khoi tao",
            notificationManager
        )
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("BBB", "onStartCommand")
        when (intent?.getStringExtra(AppConstant.ACTIVITY_START_SERVICE_KEY) ?: "") {
            AppConstant.START_COUNT_SERVICE -> {
                if (!isStart) {
                    loopCount()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("BBB", "onDestroy")
    }

    // giao dien notification
    private fun createNotification(
        context: Context,
        title: String,
        message: String,
        notificationManager: NotificationManager
    ): Notification {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        notification.setContentTitle(title)
        notification.setContentText(message)
        notification.setSmallIcon(R.drawable.ic_launcher_background)
        notification.priority = NotificationCompat.PRIORITY_DEFAULT

        // event notification
        val intent = Intent(this@MyService, MainActivity::class.java)
        intent.putExtra(AppConstant.NOTIFICATION_KEY, AppConstant.NOTIFICATION_OPEN)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        notification.setContentIntent(pendingIntent)

        // action
        val intentStart = Intent(this@MyService, MainActivity::class.java)
        intentStart.putExtra(AppConstant.NOTIFICATION_KEY, AppConstant.NOTIFICATION_START)

        val pendingIntentStart = PendingIntent.getActivity(
            context,
            0,
            intentStart,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        notification.addAction(1, "start", pendingIntentStart)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Version New",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return notification.build()
    }

    private fun loopCount() {
        CoroutineScope(Dispatchers.IO).launch {
            repeat(1000) {
                delay(1000)
                count++
                withContext(Dispatchers.Main) {
                    notification = createNotification(
                        context = this@MyService,
                        title = "Loop count",
                        message = "Count: $count",
                        notificationManager = notificationManager
                    )
                    notificationManager.notify(1, notification)
                }
            }
        }
    }

    private fun getCount(): Int {
        return count
    }

}