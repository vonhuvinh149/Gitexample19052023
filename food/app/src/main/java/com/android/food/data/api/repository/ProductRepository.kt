package com.android.food.data.api.repository

import android.content.Context
import com.android.food.data.api.RetrofitClient
import com.android.food.data.api.dto.AppResponseDTO
import com.android.food.data.api.dto.ProductDTO
import retrofit2.Call

class ProductRepository(
    var context: Context
) {

    private val apiService = RetrofitClient.getApiService(context)

    fun requestGetListProduct(): Call<AppResponseDTO<List<ProductDTO>>> {
        return apiService.getListProduct()
    }

}