package com.example.agrisynergi_mobile.database

import com.example.agrisynergi_mobile.database.DatabaseMaps.SawahResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

//
interface ApiService {
    @GET("sawah")
    suspend fun getSawahData(): SawahResponse
}


object RetrofitClient {
    private const val BASE_URL = "http://36.82.30.227:8080/api/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
