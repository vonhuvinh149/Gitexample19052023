package com.android.my_app_music.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.android.my_app_music.common.AppConstance
import com.android.my_app_music.data.service.SongService

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val action: Int = intent.getIntExtra(AppConstance.ACTION_SERVICE_KEY, 0)
            val mIntent = Intent(context, SongService::class.java)
            mIntent.putExtra(AppConstance.ACTION_RECEIVER_KEY, action)
            context?.startService(mIntent)
        }
    }
}