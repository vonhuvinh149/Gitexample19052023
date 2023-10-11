package com.android.product_api.data.model

import java.io.Serializable

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val image : String ,
    val price: Double
) : Serializable {

}