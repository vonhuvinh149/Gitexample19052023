package com.android.food.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.food.common.AppResource
import com.android.food.data.api.dto.AppResponseDTO
import com.android.food.data.api.dto.CartDTO
import com.android.food.data.api.model.Cart
import com.android.food.data.api.repository.CartRepository
import com.android.food.utils.CartUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel(context: Context) : ViewModel() {

    private val cartRepository = CartRepository(context)
    private val cartOrderLivedData = MutableLiveData<AppResource<Cart>>()
    private val cartUpdateLivedData = MutableLiveData<AppResource<Cart>>()
    private val loadingLiveData = MutableLiveData<Boolean>()

    fun getCartOrderLivedData(): LiveData<AppResource<Cart>> = cartOrderLivedData
    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData
    fun getUpdateCartLiveData(): LiveData<AppResource<Cart>> = cartUpdateLivedData

    fun requestCartConform(idCart: String) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.requestCartConform(idCart)
                .enqueue(object : Callback<AppResponseDTO<CartDTO>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<CartDTO>>,
                        response: Response<AppResponseDTO<CartDTO>>
                    ) {
                        if (response.errorBody() != null) {
                            if (response.code() == 500) {
                                val cart = CartUtils.parseCartDTO(response.body()?.data)
                                cartOrderLivedData.value = AppResource.Success(cart)
                            } else {
                                val errorResponse = response.errorBody()?.string() ?: "{}"
                                val jsonError = JSONObject(errorResponse)
                                cartOrderLivedData.value =
                                    AppResource.Error(jsonError.optString("message"))
                            }
                        } else {
                            val cart = CartUtils.parseCartDTO(response.body()?.data)
                            cartOrderLivedData.value = AppResource.Success(cart)
                        }

                        loadingLiveData.value = false
                    }

                    override fun onFailure(call: Call<AppResponseDTO<CartDTO>>, t: Throwable) {
                        cartOrderLivedData.value = AppResource.Error(t.message ?: "")
                        loadingLiveData.value = false
                    }
                })
        }
    }

    fun requestUpdateCart(idCart: String, idProduct: String, quantity: Int) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.requestUpdateCart(idCart, idProduct, quantity)
                .enqueue(object : Callback<AppResponseDTO<CartDTO>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<CartDTO>>,
                        response: Response<AppResponseDTO<CartDTO>>
                    ) {
                        if (response.isSuccessful) {
                            val cart = CartUtils.parseCartDTO(response.body()?.data)
                            cartUpdateLivedData.value = AppResource.Success(cart)
                        } else {
                            if (response.code() == 500) {
                                val cart = CartUtils.parseCartDTO(response.body()?.data)
                                cartUpdateLivedData.value = AppResource.Success(cart)
                            } else {
                                val errorResponse = response.errorBody()?.string() ?: "{}"
                                val jsonError = JSONObject(errorResponse)
                                cartOrderLivedData.value =
                                    AppResource.Error(jsonError.optString("message"))
                            }
                        }
                        loadingLiveData.value = false
                    }

                    override fun onFailure(call: Call<AppResponseDTO<CartDTO>>, t: Throwable) {
                        loadingLiveData.value = false
                    }

                })
        }
    }
}