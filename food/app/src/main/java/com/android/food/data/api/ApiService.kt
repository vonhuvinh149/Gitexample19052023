package com.android.food.data.api

import com.android.food.data.api.dto.AppResponseDTO
import com.android.food.data.api.dto.CartDTO
import com.android.food.data.api.dto.HistoryOrderDTO
import com.android.food.data.api.dto.ProductDTO
import com.android.food.data.api.dto.UserDTO
import com.android.food.data.api.model.Cart
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("user/sign-in")
    fun signIn(@Body map: HashMap<String, Any>): Call<AppResponseDTO<UserDTO>>

    @POST("user/sign-up")
    fun signUp(@Body map: HashMap<String, Any>): Call<AppResponseDTO<UserDTO>>

    @GET("product")
    fun getListProduct(): Call<AppResponseDTO<List<ProductDTO>>>

    @GET("cart")
    fun getCart(): Call<AppResponseDTO<CartDTO>>

    @POST("cart/add")
    fun addCart(@Body map: HashMap<String, Any>): Call<AppResponseDTO<CartDTO>>

    @Headers("Accept: application/json")
    @POST("cart/conform")
    fun cartConform(@Body map: HashMap<String, Any>): Call<AppResponseDTO<CartDTO>>

    @POST("order/history")
    fun requestHistoryOrder(): Call<AppResponseDTO<List<HistoryOrderDTO>>>

    @POST("cart/update")
    fun requestUpdateCart(@Body map: HashMap<String, Any>): Call<AppResponseDTO<CartDTO>>

}