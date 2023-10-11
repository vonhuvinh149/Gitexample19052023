package com.android.mvvm.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.mvvm.data.database.converter.BitmapConverter

@Database(entities = [ProductEntity::class], version = 1)
@TypeConverters(BitmapConverter::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        var instance: ProductDatabase? = null
        fun getDatabase(context: Context): ProductDatabase {
            var tmpInstance = instance
            if (tmpInstance == null) {
                tmpInstance = Room.databaseBuilder(
                    context,
                    ProductDatabase::class.java,
                    "product-database"
                ).build()
            }
            return tmpInstance
        }
    }
}