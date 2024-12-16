package com.example.agrisynergi_mobile.database.DatabaseMaps

import com.example.agrisynergi_mobile.retrofit.network.ApiService
import javax.inject.Inject

class SawahRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getSawahData(): List<Sawah> {
        val response = apiService.getSawahList()
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Failed to load data")
        }
    }
}

