package com.android.product_api.data.repository

import com.android.product_api.data.api.response.ApiResponse
import com.android.product_api.data.api.ApiService
import com.android.product_api.data.model.Product
import retrofit2.Response

class ProductRepository(private val apiService: ApiService) {
    suspend fun getAllProduct(): Response<ApiResponse> {
        return apiService.getAllProduct()
    }

    suspend fun findById(id: Long): Product {
        return apiService.findById(id)
    }

    suspend fun deleteProduct(id: Long) {
        apiService.deleteById(id)
    }

    suspend fun updateProduct(id: Long, product: Product): Product {
        return apiService.updateProduct(id, product)
    }

    suspend fun createProduct(product: Product) : Product{
        return apiService.createProduct(product)
    }
}