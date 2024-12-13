package com.example.agrisynergi_mobile.database

import com.example.agrisynergi_mobile.database.DatabaseMaps.SawahResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("sawah")
    suspend fun getSawahByLokasi(@Query("lokasi") lokasi: String): Response<SawahResponse>

    // New endpoint to fetch all sawah
    @GET("sawah")
    suspend fun getSawahList(): Response<SawahResponse> // Fetch all sawah data
}


object RetrofitClient {
    private const val BASE_URL = "http://36.82.30.227:8080/api/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
