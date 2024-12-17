
package com.example.agrisynergi_mobile.storage

import androidx.room.*
import com.example.agrisynergi_mobile.storage.FarmerEntity

@Dao
interface FarmerDao {

    @Insert
    suspend fun insertFarmer(farmer: FarmerEntity): Long

    @Delete
    suspend fun deleteFarmer(farmer: FarmerEntity): Int

    @Update
    suspend fun updateFarmer(farmer: FarmerEntity): Int

    // Queries to fetch data
    @Query("SELECT * FROM farmers")
    suspend fun getAllFarmers(): List<FarmerEntity>

    @Query("SELECT * FROM farmers WHERE id = :id")
    suspend fun getFarmerById(id: Int): FarmerEntity?
}




