package com.example.agrisynergi_mobile.database.ModelKomunitas

import com.example.agrisynergi_mobile.retrofit.network.ApiService
import javax.inject.Inject

class KomentarRepository @Inject constructor(
    private val apiService: ApiService
) {

    // Function to get comments by komunitas id
    suspend fun getKomentarByKomunitasId(idKomunitas: Int): List<Komentator> {
        val response = apiService.getKomentator() // Assuming the service fetches all comments
        return if (response.isSuccessful) {
            response.body()?.data?.filter { it.id_komunitas == idKomunitas } ?: emptyList()
        } else {
            throw Exception("Failed to fetch comments")
        }
    }
}
