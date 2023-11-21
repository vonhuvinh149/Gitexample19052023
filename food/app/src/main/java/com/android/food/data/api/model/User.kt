package com.android.food.data.api.model

data class User(
    val email: String = "",
    val name: String = "",
    val phone: String = "",
    var token: String = "",
    val registerDate: String = ""
)
