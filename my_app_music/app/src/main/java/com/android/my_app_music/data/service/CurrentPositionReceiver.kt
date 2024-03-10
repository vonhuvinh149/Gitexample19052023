package com.android.my_app_music.data.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.my_app_music.common.AppConstance

class CurrentPositionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val currentPosition = intent.getIntExtra(AppConstance.CURRENT_POSITION, 0)
            val curIntent = Intent("CURRENT_POSITION")
            curIntent.putExtra(AppConstance.CURRENT_POSITION, currentPosition)
            context?.sendBroadcast(curIntent)
        }
    }
}