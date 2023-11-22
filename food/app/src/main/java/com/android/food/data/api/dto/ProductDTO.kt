package com.android.food.data.api.dto

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("_id")
    val id: String?,
    val name: String?,
    val address: String?,
    val price: Long,
    val img: String?,
    val quantity: Int?,
    @SerializedName("gallery")
    val gallery: List<String>?,
    @SerializedName("date_created")
    val dateCreated: String,
    @SerializedName("date_updated")
    val dateUpdated: List<String>?,
    @SerializedName("__v")
    val v: Long,
)
