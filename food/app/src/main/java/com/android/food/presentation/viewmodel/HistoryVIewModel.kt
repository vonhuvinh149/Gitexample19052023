package com.android.food.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.food.common.AppResource
import com.android.food.data.api.dto.AppResponseDTO
import com.android.food.data.api.dto.HistoryOrderDTO
import com.android.food.data.api.model.HistoryOrder
import com.android.food.data.api.repository.HistoryRepository
import com.android.food.utils.HistoryUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryVIewModel(context: Context) : ViewModel() {

    private val historyRepository = HistoryRepository(context)

    private var historyOrderLiveData = MutableLiveData<AppResource<List<HistoryOrder>>>()
    private var loadingLiveData = MutableLiveData<Boolean>()

    fun getHistoryOrderLiveData(): LiveData<AppResource<List<HistoryOrder>>> = historyOrderLiveData
    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData

    fun requestHistoryOrder() {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.requestHistory()
                .enqueue(object : Callback<AppResponseDTO<List<HistoryOrderDTO>>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<List<HistoryOrderDTO>>>,
                        response: Response<AppResponseDTO<List<HistoryOrderDTO>>>
                    ) {
                        if (response.isSuccessful) {
                            val historyOrder = response.body()?.data?.map {
                                HistoryUtils.parseHistoryDTO(it)
                            }
                            historyOrderLiveData.value = AppResource.Success(historyOrder)
                        } else {
                            val errorResponse = response.errorBody()?.string() ?: "{}"
                            val jsonError = JSONObject(errorResponse)
                            historyOrderLiveData.value =
                                AppResource.Error(jsonError.optString("message"))
                        }
                        loadingLiveData.value = false
                    }

                    override fun onFailure(
                        call: Call<AppResponseDTO<List<HistoryOrderDTO>>>,
                        t: Throwable
                    ) {
//                        historyOrderLiveData.value = AppResource.Error(t.message.toString())
                        loadingLiveData.value = false
                    }

                })
        }
    }
}