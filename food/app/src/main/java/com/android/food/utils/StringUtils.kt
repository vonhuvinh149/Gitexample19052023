package com.android.food.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object StringUtils {
    fun formatCurrency(number: Int): String {
        return DecimalFormat("#,###").format(number)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(str: String): String {
        val instant = Instant.parse(str)
        val localDateTime = LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return localDateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(str: String): String {
        val instant = Instant.parse(str)
        val localDateTime = LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return localDateTime.format(formatter)
    }
}