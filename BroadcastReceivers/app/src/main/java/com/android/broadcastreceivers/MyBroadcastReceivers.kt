package com.android.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.widget.Toast

class MyBroadcastReceivers : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == WifiManager.WIFI_STATE_CHANGED_ACTION) {
            val isWifiModeOn =
                intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
            when (isWifiModeOn) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    Toast.makeText(context, "Wi-Fi đã được bật", Toast.LENGTH_SHORT).show()
                }

                WifiManager.WIFI_STATE_DISABLED -> {
                    Toast.makeText(context, "Wi-Fi đã được tat", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}