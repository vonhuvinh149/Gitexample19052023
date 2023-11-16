package com.android.food.data.api.dto

import com.google.gson.annotations.SerializedName

data class CartDTO(
    @SerializedName("_id")
    val id: String?,
    val products: List<ProductDTO>?,
    @SerializedName("id_user")
    val idUser: String?,
    val price: Long?,
    @SerializedName("date_created")
    val dateCreated: String?,
)
