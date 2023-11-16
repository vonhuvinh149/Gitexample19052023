package com.android.food.data.api.model

import com.google.gson.Gson

data class User(
    val email: String = "" ,
    val name: String = "",
    val phone: String = "",
    val token: String = "",
    val registerDate : String = ""
)
