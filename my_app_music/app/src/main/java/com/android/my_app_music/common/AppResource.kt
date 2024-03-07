package com.android.my_app_music.common

sealed class AppResource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : AppResource<T>(data)
    class Error<T>(msg: String?) : AppResource<T>(message = msg)
}