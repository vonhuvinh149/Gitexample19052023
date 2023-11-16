package com.android.food.data.api.model

import java.io.Serializable

data class HistoryOrder(
    val id: String = "",
    val products: List<Product> = emptyList(),
    val idUser: String = "",
    val price: Long = 0,
    val status: Boolean = false,
    val dateCreated: String = ""
) : Serializable