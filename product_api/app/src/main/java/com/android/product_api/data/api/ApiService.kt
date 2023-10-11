package com.android.product_api.data.api

import com.android.product_api.data.api.response.ApiResponse
import com.android.product_api.data.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("products")
    suspend fun getAllProduct(): Response<ApiResponse>

    @GET("products/{id}")
    suspend fun findById(@Path("id") id: Long) : Product

    @DELETE("products/{id}")
    suspend fun deleteById(@Path("id") id: Long)

    @PATCH("products/{id}")
    suspend fun updateProduct(@Path("id") id : Long , @Body product: Product) : Product

    @POST("products/create")
    suspend fun createProduct(@Body product: Product) : Product

}