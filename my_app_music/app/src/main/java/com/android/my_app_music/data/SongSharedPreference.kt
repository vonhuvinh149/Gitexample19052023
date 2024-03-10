package com.android.my_app_music.data

import android.content.Context

class SongSharedPreference(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveIsCheckRepeat(isCheck: Boolean) {
        editor.putBoolean("repeat-key", isCheck)
        editor.apply()
        editor.commit()
    }

    fun saveIsCheckShuffle(isCheck: Boolean) {
        editor.putBoolean("shuffle-key", isCheck)
        editor.apply()
        editor.commit()
    }

    fun getIsRepeat(): Boolean {
        return sharedPreferences.getBoolean("repeat-key", false)
    }

    fun getIsShuffle(): Boolean {
        return sharedPreferences.getBoolean("shuffle-key", false)
    }
}