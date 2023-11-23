package com.android.food.data.api.model

import java.io.Serializable

data class Product(
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var price: Long = 0,
    var image: String = "",
    var quantity: Int = 0,
    var dateUpdated : String = "" ,
    var gallery: List<String> = emptyList()
) : Serializable
