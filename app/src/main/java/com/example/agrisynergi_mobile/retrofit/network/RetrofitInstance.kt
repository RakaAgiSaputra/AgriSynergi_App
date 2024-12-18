package com.example.agrisynergi_mobile.retrofit.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 object RetrofitInstance {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://gtk62vzp-3000.asse.devtunnels.ms/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
