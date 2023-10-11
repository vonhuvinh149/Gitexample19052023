package com.android.mvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mvvm.common.AppResource
import com.android.mvvm.data.database.ProductEntity
import com.android.mvvm.data.repository.ProductRepository
import com.android.mvvm.extension.launchOnMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)


    private var liveDataListProducts = MutableLiveData<AppResource<List<ProductEntity>>>()
    private var liveDataInsertProduct = MutableLiveData<AppResource<Any?>>()
    private var liveDataDeleteProduct = MutableLiveData<AppResource<Any?>>()
    private val liveDataLoading = MutableLiveData<Boolean>()

    fun listProductLiveData(): LiveData<AppResource<List<ProductEntity>>> = liveDataListProducts

    fun insertLiveData(): LiveData<AppResource<Any?>> = liveDataInsertProduct

    fun loadingLivedata(): LiveData<Boolean> = liveDataLoading

    fun getListProducts() {
        liveDataLoading.value = false
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listProducts = async { productRepository.getListProducts() }.await()
                launchOnMain {
                    liveDataListProducts.value = AppResource.Success(listProducts)
                }
            } catch (e: Exception) {
                launchOnMain {
                    liveDataListProducts.value = AppResource.Error(e.message ?: "")
                }
            } finally {
                launchOnMain {
                    liveDataLoading.value = true
                }
            }
        }
    }

    fun insertProduct(productEntity: ProductEntity) {
        liveDataLoading.value = false
        viewModelScope.launch(Dispatchers.IO) {
            delay(2500)
            launch {
                try {
                    productRepository.insertProduct(productEntity)
                    launchOnMain {
                        liveDataInsertProduct.value = AppResource.Success(null)
                        getListProducts()
                    }
                } catch (e: Exception) {
                    launchOnMain {
                        liveDataInsertProduct.value = AppResource.Error(e.message ?: "")
                    }
                } finally {
                    launchOnMain {
                        liveDataLoading.value = true
                    }
                }
            }

        }
    }

    fun deleteProduct(id: Int) {
        liveDataLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                try {
                    productRepository.deleteProduct(id)
                    launchOnMain {
                        liveDataDeleteProduct.value = AppResource.Success(null)
                        getListProducts()
                    }
                } catch (e: Exception) {
                    launchOnMain {
                        liveDataInsertProduct.value = AppResource.Error(e.message ?: "")
                    }
                } finally {
                    launchOnMain {
                        liveDataLoading.value = false
                    }
                }
            }
        }
    }
}