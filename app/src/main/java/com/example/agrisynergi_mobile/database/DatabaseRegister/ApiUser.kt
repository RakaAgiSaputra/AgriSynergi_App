package com.example.agrisynergi_mobile.database.DatabaseRegister

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


//Api
interface Api {
    @GET("api/users") // arah ke tabel user
    fun getUsers(): Call<UserResponse>

    @FormUrlEncoded
    @POST("api/users") // post data ke tabel user
    fun register(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("no_hp") phoneNumber: String,
        @Field("alamat") address: String,
        @Field("katasandi") password: String
    ): Call<UserResponse>
}





