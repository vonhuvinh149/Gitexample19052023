package com.android.food.data.api.model

data class Cart(
    val id: String = "",
    val products: List<Product> = emptyList(),
    val idUser: String = "",
    val price: Long = 0,
    val dateCreated: String = "",
)
