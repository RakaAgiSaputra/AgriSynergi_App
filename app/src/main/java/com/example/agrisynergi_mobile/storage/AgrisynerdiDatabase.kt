package com.example.agrisynergi_mobile.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FarmerEntity::class], version = 1, exportSchema = false)
abstract class AgrisynergiDatabase : RoomDatabase() {
    abstract fun farmerDao(): FarmerDao

    companion object {
        @Volatile
        private var INSTANCE: AgrisynergiDatabase? = null

        fun getDatabase(context: Context): AgrisynergiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AgrisynergiDatabase::class.java,
                    "agrisynergi_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
