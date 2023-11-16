package com.android.food.common

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.android.food.data.api.model.User
import com.google.gson.Gson

class AppSharePreference(
    context: Context
) {

    private val key = AppConstant.TOKEN_KEY

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            AppConstant.SHARE_PREFERENCE_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
    }

    private val editor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    // convert object -> json
    private fun User.toJson(): String {
        return Gson().toJson(this)
    }

    // convert  json -> object
    fun String.toUser(): User {
        return Gson().fromJson(this, User::class.java)
    }

    fun saveUser(user: User) {
        val jsonString = user.toJson()
        sharedPreferences.edit {
            editor.putString(key, jsonString)
            editor.commit()
        }
    }

    fun getUser(): User? {
        val jsonString = sharedPreferences.getString(key, null)
        return jsonString?.toUser()
    }

    fun logout() {
        sharedPreferences.edit {
            editor.remove(key)
            editor.commit()
        }
    }

}