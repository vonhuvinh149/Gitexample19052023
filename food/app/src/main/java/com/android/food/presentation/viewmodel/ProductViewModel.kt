package com.android.food.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.food.common.AppResource
import com.android.food.data.api.dto.AppResponseDTO
import com.android.food.data.api.dto.CartDTO
import com.android.food.data.api.dto.ProductDTO
import com.android.food.data.api.model.Cart
import com.android.food.data.api.repository.ProductRepository
import com.android.food.data.api.model.Product
import com.android.food.data.api.repository.CartRepository
import com.android.food.utils.CartUtils
import com.android.food.utils.ProductUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(context: Context) : ViewModel() {

    private var productRepository = ProductRepository(context)
    private var cartRepository = CartRepository(context)

    private var loadingLiveData = MutableLiveData<Boolean>()
    private var listProductLiveData = MutableLiveData<AppResource<List<Product>>>()
    private val cartLiveData = MutableLiveData<AppResource<Cart>>()

    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData
    fun getProductLiveData(): LiveData<AppResource<List<Product>>> = listProductLiveData
    fun getCartLiveData(): LiveData<AppResource<Cart>> = cartLiveData

    // Get Product
    fun executeGetProduct() {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            productRepository
                .requestGetListProduct()
                .enqueue(object : Callback<AppResponseDTO<List<ProductDTO>>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<List<ProductDTO>>>,
                        response: Response<AppResponseDTO<List<ProductDTO>>>
                    ) {
                        if (response.isSuccessful) {
                            val listProducts =
                                response.body()?.data?.map { ProductUtils.parseProductDTO(it) }
                            listProductLiveData.value = AppResource.Success(listProducts)
                        } else {
                            val errorResponse = response.errorBody()?.string() ?: "{}"
                            val jsonError = JSONObject(errorResponse)
                            listProductLiveData.value =
                                AppResource.Error(jsonError.optString("message"))
                        }
                        loadingLiveData.value = false
                    }

                    override fun onFailure(
                        call: Call<AppResponseDTO<List<ProductDTO>>>,
                        t: Throwable
                    ) {
                        listProductLiveData.value = AppResource.Error(t.message.toString())
                        loadingLiveData.value = false
                    }

                })
        }
    }

    // Get Cart
    fun executeGetCart() {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository
                .requestGetCart()
                .enqueue(object : Callback<AppResponseDTO<CartDTO>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<CartDTO>>,
                        response: Response<AppResponseDTO<CartDTO>>
                    ) {
                        if (response.errorBody() != null) {
                            if (response.code() == 500) {
                                cartLiveData.value = AppResource.Success(Cart())
                            } else {
                                val errorResponse = response.errorBody()?.string() ?: "{}"
                                val jsonError = JSONObject(errorResponse)
                                cartLiveData.value =
                                    AppResource.Error(jsonError.optString("message"))
                            }
                        } else {
                            val cart = CartUtils.parseCartDTO(response.body()?.data)
                            cartLiveData.value = AppResource.Success(cart)
                        }

                        loadingLiveData.value = false
                    }

                    override fun onFailure(
                        call: Call<AppResponseDTO<CartDTO>>,
                        t: Throwable
                    ) {
                        cartLiveData.value = AppResource.Error(t.message.toString())
                    }
                })
        }
    }

    // Add to cart
    fun executeAddToCart(idProduct: String) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository
                .requestAddToCart(idProduct)
                .enqueue(object : Callback<AppResponseDTO<CartDTO>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<CartDTO>>,
                        response: Response<AppResponseDTO<CartDTO>>
                    ) {
                        if (response.errorBody() != null) {
                            if (response.code() == 500) {
                                cartLiveData.value = AppResource.Success(Cart())
                            } else {
                                val errorResponse = response.errorBody()?.string() ?: "{}"
                                val jsonError = JSONObject(errorResponse)
                                cartLiveData.value =
                                    AppResource.Error(jsonError.optString("message"))
                            }
                        } else {
                            val cart = CartUtils.parseCartDTO(response.body()?.data)
                            cartLiveData.value = AppResource.Success(cart)
                        }

                        loadingLiveData.value = false
                    }

                    override fun onFailure(
                        call: Call<AppResponseDTO<CartDTO>>,
                        t: Throwable
                    ) {
                        cartLiveData.value = AppResource.Error(t.message.toString())
                    }
                })
        }
    }

}