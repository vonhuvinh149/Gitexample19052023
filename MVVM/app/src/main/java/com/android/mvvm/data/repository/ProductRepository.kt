package com.android.mvvm.data.repository

import android.content.Context
import com.android.mvvm.data.database.ProductDao
import com.android.mvvm.data.database.ProductDatabase
import com.android.mvvm.data.database.ProductEntity

class ProductRepository(context: Context) {

    var productDao = ProductDatabase.getDatabase(context).productDao()

    suspend fun getListProducts(): List<ProductEntity> {
        return productDao.queryProducts()
    }

    suspend fun insertProduct(productEntity: ProductEntity) {
        productDao.createProduct(productEntity)
    }

    suspend fun deleteProduct(id: Int){
        productDao.deleteProduct(id)
    }
}