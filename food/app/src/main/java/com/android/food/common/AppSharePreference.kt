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
    private fun String.toUser(): User {
        return Gson().fromJson(this, User::class.java)
    }

    fun saveUser(user: User) {
        val jsonString = user.toJson()
        val currentTime = System.currentTimeMillis() + (5 * 60 * 1000)
        sharedPreferences.edit {
            editor.putString(AppConstant.TOKEN_KEY, jsonString)
            editor.putLong(AppConstant.EXPIRATION_TIME_KEY, currentTime)
            editor.commit()
        }
    }

    private fun getExpirationTime(): Long {
        return sharedPreferences.getLong(AppConstant.EXPIRATION_TIME_KEY, 0)
    }

    fun isTokenExpirationTime(): Boolean {
        val currentTime = System.currentTimeMillis()
        val expirationTime = getExpirationTime()
        return expirationTime > currentTime
    }

    fun isTokenValid(): Boolean {
        val token: String = getUser()?.token ?: ""
        return token.isNotBlank()
    }

    fun getUser(): User? {
        val jsonString = sharedPreferences.getString(AppConstant.TOKEN_KEY, null)
        return jsonString?.toUser()
    }

    fun refreshToken(token: String) {
        val user: User = getUser() ?: User()
        val updateUser = user.copy(token = token)
        val currentTime = System.currentTimeMillis() + (5 * 60 * 1000)
        sharedPreferences.edit {
            editor.putString(AppConstant.TOKEN_KEY, updateUser.toJson())
            editor.putLong(AppConstant.EXPIRATION_TIME_KEY, currentTime)
            editor.commit()
        }
    }

    fun logout() {
        sharedPreferences.edit {
            editor.remove(AppConstant.TOKEN_KEY)
            editor.commit()
        }
    }
}