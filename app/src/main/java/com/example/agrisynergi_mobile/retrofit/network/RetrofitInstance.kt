package com.example.agrisynergi_mobile.retrofit.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

//        .baseUrl("https://knfqvrln-3000.asse.devtunnels.ms/")

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://110.139.0.123:8080/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
