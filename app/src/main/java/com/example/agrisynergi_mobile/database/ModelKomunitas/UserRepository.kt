package com.example.agrisynergi_mobile.database.ModelKomunitas

import com.example.agrisynergi_mobile.retrofit.model.User
import com.example.agrisynergi_mobile.retrofit.network.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    // Fetch users from the API
    suspend fun getUsers(): List<User> {
        return try {
            val response = apiService.getUsers()

            if (response.success) {
                response.data
            } else {
                println("Failed to fetch users: ${response.message}")
                emptyList()
            }
        } catch (e: Exception) {
            println("Error fetching users: ${e.message}")
            emptyList()
        }
    }
}
