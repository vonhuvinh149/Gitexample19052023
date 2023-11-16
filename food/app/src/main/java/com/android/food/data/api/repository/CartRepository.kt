package com.android.food.data.api.repository


import android.content.Context
import com.android.food.data.api.RetrofitClient
import com.android.food.data.api.dto.AppResponseDTO
import com.android.food.data.api.dto.CartDTO
import retrofit2.Call

class CartRepository(
    var context: Context
) {
    private val apiService = RetrofitClient.getApiService(context)

    fun getCart(): Call<AppResponseDTO<CartDTO>> {
        return apiService.getCart()
    }

    fun requestAddToCart(idProduct: String): Call<AppResponseDTO<CartDTO>> {
        val map = HashMap<String, Any>()
        map["id_product"] = idProduct
        return apiService.addCart(map)
    }

    fun requestCartConform(idCart: String): Call<AppResponseDTO<CartDTO>> {
        val map = HashMap<String, Any>()
        map["id_cart"] = idCart
        map["status"] = false
        return apiService.cartConform(map)
    }

    fun requestUpdateCart(
        idCart: String,
        idProduct: String,
        quantity: Int
    ): Call<AppResponseDTO<CartDTO>> {
        val map = HashMap<String, Any>()
        map["id_product"] = idProduct
        map["id_cart"] = idCart
        map["quantity"] = quantity
        return apiService.requestUpdateCart(map)
    }
}