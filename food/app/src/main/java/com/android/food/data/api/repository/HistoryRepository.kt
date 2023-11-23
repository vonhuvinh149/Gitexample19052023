package com.android.food.data.api.repository

import android.content.Context
import com.android.food.data.api.RetrofitClient
import com.android.food.data.api.dto.AppResponseDTO
import com.android.food.data.api.dto.HistoryOrderDTO
import retrofit2.Call

class HistoryRepository(val context: Context) {
    private val apiService = RetrofitClient.getApiService(context)

    fun requestGetHistory(): Call<AppResponseDTO<List<HistoryOrderDTO>>> {
        return apiService.requestHistoryOrder()
    }
}