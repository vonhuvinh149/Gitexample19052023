package com.android.my_app_music.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.my_app_music.common.AppConstance
import com.android.my_app_music.data.model.Song

class DataChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val position = intent.getIntExtra(AppConstance.CHANGE_POSITION_FROM_SERVICE, 0)
            val isPlay = intent.getBooleanExtra(AppConstance.CHECK_IS_PLAY, false)
            val duration = intent.getIntExtra(AppConstance.DURATION_POSITION, 0)
            val mIntent = Intent("ACTION_CHECK")
            mIntent.putExtra(AppConstance.CHANGE_POSITION_FROM_SERVICE, position)
            mIntent.putExtra(AppConstance.CHECK_IS_PLAY, isPlay)
            mIntent.putExtra(AppConstance.DURATION_POSITION, duration)
            context?.sendBroadcast(mIntent)
        }
    }
}