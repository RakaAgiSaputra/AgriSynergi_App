package com.example.agrisynergi_mobile.retrofit.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://36.74.38.214:8080/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
