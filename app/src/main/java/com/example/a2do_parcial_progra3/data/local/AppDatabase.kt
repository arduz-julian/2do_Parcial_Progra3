package com.example.a2do_parcial_progra3.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a2do_parcial_progra3.data.local.dao.ExchangeRateDao
import com.example.a2do_parcial_progra3.data.local.dao.PostDao
import com.example.a2do_parcial_progra3.data.local.dao.ProductDao
import com.example.a2do_parcial_progra3.data.local.entity.ExchangeRateEntity
import com.example.a2do_parcial_progra3.data.local.entity.PostEntity
import com.example.a2do_parcial_progra3.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class, ExchangeRateEntity::class, PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun exchangeRateDao(): ExchangeRateDao
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
