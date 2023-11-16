package com.android.food.data.api.dto

import com.android.food.data.api.model.Product
import com.google.gson.annotations.SerializedName

data class HistoryOrderDTO(
    @SerializedName("_id")
    val id: String?,
    val products: List<Product>?,
    @SerializedName("id_user")
    val idUser: String?,
    val price: Long?,
    val status: Boolean?,
    @SerializedName("date_created")
    val dateCreated: String?
)