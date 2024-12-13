package com.example.agrisynergi_mobile.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farmers")
data class FarmerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val location: String,
    val cropType: String,
    val farmSize: Double // luas lahan dalam hektar
)
