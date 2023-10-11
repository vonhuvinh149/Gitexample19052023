package com.android.product_api.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.product_api.common.AppResourceState
import com.android.product_api.data.model.Product
import com.android.product_api.data.repository.ProductRepository
import com.android.product_api.extention.launchOnMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val productsLiveData = MutableLiveData<AppResourceState<List<Product>>>()
    private val liveDataLoading = MutableLiveData<Boolean>()
    private val productDetailLiveData = MutableLiveData<AppResourceState<Product>>()
    private val deleteProductLiveData = MutableLiveData<AppResourceState<Any>>()
    private val updateProductLiveData = MutableLiveData<AppResourceState<Product>>()
    private val createProductLiveData = MutableLiveData<AppResourceState<Product>>()

    fun productsLiveData(): LiveData<AppResourceState<List<Product>>> = productsLiveData
    fun loadingLiveData(): LiveData<Boolean> = liveDataLoading
    fun detailLiveData(): LiveData<AppResourceState<Product>> = productDetailLiveData
    fun deleteLiveData(): LiveData<AppResourceState<Any>> = deleteProductLiveData
    fun updateLiveData(): LiveData<AppResourceState<Product>> = updateProductLiveData
    fun createLiveData(): LiveData<AppResourceState<Product>> = createProductLiveData

    fun getProduct() {
        viewModelScope.launch {
            liveDataLoading.value = false
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = async { productRepository.getAllProduct() }.await()
                    val products = response.body()?.content ?: emptyList()
                    launchOnMain {
                        productsLiveData.value = AppResourceState.Success(products)
                    }
                } catch (e: Exception) {
                    launchOnMain {
                        productsLiveData.value = AppResourceState.Error(e.message ?: "")
                    }
                } finally {
                    launchOnMain { liveDataLoading.value = true }
                }
            }
        }
    }

    fun findByIdProduct(id: Long) {
        viewModelScope.launch {
            liveDataLoading.value = false
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val myProduct = async { productRepository.findById(id) }.await()
                    launchOnMain {
                        productDetailLiveData.value = AppResourceState.Success(myProduct)
                    }
                } catch (e: Exception) {
                    launchOnMain {
                        productDetailLiveData.value = AppResourceState.Error(e.message ?: "")
                    }
                } finally {
                    launchOnMain { liveDataLoading.value = true }
                }
            }
        }
    }

    fun deleteProductById(id: Long) {
        viewModelScope.launch {
            liveDataLoading.value = false
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    productRepository.deleteProduct(id)
                    launchOnMain {
                        deleteProductLiveData.value = AppResourceState.Success("xoa thanh cong")
                    }
                } catch (e: Exception) {
                    launchOnMain {
                        deleteProductLiveData.value = AppResourceState.Error(e.message ?: "")
                    }
                } finally {
                    launchOnMain { liveDataLoading.value = true }
                }
            }
        }
    }

    fun updateProductById(id: Long, product: Product) {
        viewModelScope.launch {
            liveDataLoading.value = false
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val productUpdate =
                        async { productRepository.updateProduct(id, product) }.await()
                    launchOnMain {
                        updateProductLiveData.value = AppResourceState.Success(productUpdate)
                    }
                } catch (e: Exception) {
                    launchOnMain {
                        updateProductLiveData.value = AppResourceState.Error(e.message ?: "")
                    }
                } finally {
                    launchOnMain { liveDataLoading.value = true }
                }
            }
        }
    }

    fun createProduct(product: Product) {
        viewModelScope.launch {
            liveDataLoading.value = false
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val newProduct = async { productRepository.createProduct(product) }.await()
                    launchOnMain {
                        createProductLiveData.value = AppResourceState.Success(newProduct)
                    }
                } catch (e: Exception) {
                    launchOnMain {
                        createProductLiveData.value = AppResourceState.Error(e.message ?: "")
                    }
                } finally {
                    launchOnMain { liveDataLoading.value = true }
                }
            }
        }
    }
}