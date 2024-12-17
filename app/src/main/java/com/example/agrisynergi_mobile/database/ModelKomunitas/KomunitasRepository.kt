package com.example.agrisynergi_mobile.database.ModelKomunitas

import com.example.agrisynergi_mobile.database.ModelKomunitas.CommunityResponse
import com.example.agrisynergi_mobile.retrofit.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class CommunityRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchCommunityData(): CommunityResponse {

        return apiService.getCommunityData()
    }

    // Mengirim data komunita
    suspend fun postCommunityData(
        idUser: RequestBody,
        image: MultipartBody.Part,
        description: RequestBody
    ): Result<CommunityResponse> {
        return try {
            val response = apiService.addCommunityPost(idUser, image, description)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Result.Success(body)
                } else {
                    Result.Error("Failed to post community data: ${body?.message}")
                }
            } else {
                Result.Error("HTTP Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Error: ${e.localizedMessage}")
        }
    }

}