package com.android.mvvm.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Query("select * from product")
    suspend fun queryProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createProduct(productEntity: ProductEntity)

    @Query("delete from product where id=:id")
    suspend fun deleteProduct(id: Int)

}