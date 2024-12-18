package com.example.agrisynergi_mobile.database

import com.example.agrisynergi_mobile.database.DatabaseRegister.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//RetrofilClient
class RetrofitClient1 {
    companion object {
        private const val BASE_URL = "https://gtk62vzp-3000.asse.devtunnels.ms/" //ganti url Api

        val instance: Api by lazy {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}
