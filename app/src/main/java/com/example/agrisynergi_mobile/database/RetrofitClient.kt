package com.example.agrisynergi_mobile.database

import com.example.agrisynergi_mobile.database.DatabaseRegister.Api
import com.example.agrisynergi_mobile.database.DatabaseProduk.ApiProduk
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//RetrofilClient
class RetrofitClient {
    companion object {
        private const val BASE_URL = "http://36.74.31.200:8080/" //ganti url Api

        val instance: Api by lazy {
            createRetrofitInstance().create(Api::class.java)
        }

        val apiProduk: ApiProduk by lazy {
            createRetrofitInstance().create(ApiProduk::class.java)
        }

        private fun createRetrofitInstance(): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
