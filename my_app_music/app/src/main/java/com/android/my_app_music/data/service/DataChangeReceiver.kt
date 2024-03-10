package com.android.my_app_music.data.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.my_app_music.common.AppConstance

class DataChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val position = intent.getIntExtra(AppConstance.CHANGE_POSITION_FROM_SERVICE, 0)
            val isPlay = intent.getBooleanExtra(AppConstance.CHECK_IS_PLAY, true)
            Log.d("BBB" , "zzz $position")
            val mIntent = Intent("YOUR_ACTION_NAME")
            mIntent.putExtra(AppConstance.CHANGE_POSITION_FROM_SERVICE, position)
            mIntent.putExtra(AppConstance.CHECK_IS_PLAY, isPlay)
            context?.sendBroadcast(mIntent)
        }
    }
}