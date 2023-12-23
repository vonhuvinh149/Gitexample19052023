package com.android.firebase_1

data class User(
    val name: String,
    val age: Int
){
    constructor() : this("" , 0)
}
