package com.android.food.utils.validation

class Validator {
    private val emailPattern = Regex("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}\$")
    private val namePattern = Regex("^[A-Za-z\\s-]+\$")
    private val passwordPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private val phonePattern = Regex("^(0[0-9]{9}|84[0-9]{8})\$")

    fun isValidateEmail(email: String): String {
        if (email.isBlank()) {
            return "Please provide your email address."
        }
        if (!emailPattern.matches(email)) {
            return "Invalid email format"
        }
        return "";
    }

    fun isValidateName(name: String): String {
        if (name.isBlank()) {
            return "Please provide your name."
        } else if (!namePattern.matches(name)) {
            return "Invalid name format"
        }
        return "";
    }

    fun isValidatePassword(password: String): String {
        if (password.isBlank()) {
            return "Please provide your password."
        } else if (password.length < 8) {
            return "Password must be at least 8 characters long."
        }
//        else if (!passwordPattern.matches(password)) {
//            return "Invalid password format"
//        }
        return ""
    }

    fun isValidatePhone(phone: String): String {
        if (phone.isBlank()) {
            return "Please provide your phone."
        } else if (phone.length < 10) {
            return "phone must be at least 10 characters long."
        } else if (!phonePattern.matches(phone)) {
            return "Invalid phone format"
        }
        return "";
    }

    fun isValidateAddress(address: String): String {
        if (address.isBlank()) {
            return "Please provide your address."
        }
        if (address.length < 10) {
            return "Please enter in address full."
        }
        return "";
    }

}