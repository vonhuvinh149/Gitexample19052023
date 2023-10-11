package com.android.mvvm.data.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    val id: Int?,
    val name: String?,
    val description: String?,
    val price: Double?,
    val image: Bitmap?
)