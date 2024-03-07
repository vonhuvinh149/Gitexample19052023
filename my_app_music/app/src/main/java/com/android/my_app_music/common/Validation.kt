package com.android.my_app_music.common

import android.util.Patterns
import java.util.regex.Pattern

object Validation {
    private val passwordPattern =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")

    fun isValidEmail(email: String): String {
        val pattern = Patterns.EMAIL_ADDRESS
        var msg: String = ""
        if (email.isEmpty()) {
            msg = "vui lòng nhập"
        } else if (!pattern.matcher(email).matches()) {
            msg = "email sai định dạng"
        } else if (email.length < 12) {
            msg = "email không được dưới 12 kí tự"
        }
        return msg
    }

    fun isValidPassword(password: String): String {
        var msg: String = ""
        if (password.isEmpty()) {
            msg = "vui lòng nhập"
        } else if (password.length < 8) {
            msg = "mật khẩu ít nhất 8 kí tự"
        } else if (!passwordPattern.matches(password)) {
            msg = "sai định dạng"
        }
        return msg
    }
}