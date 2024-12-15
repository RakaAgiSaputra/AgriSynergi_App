package com.example.agrisynergi_mobile.retrofit.network

import com.example.agrisynergi_mobile.database.DatabaseMaps.SawahResponse
import com.example.agrisynergi_mobile.retrofit.model.LoginRequest
import com.example.agrisynergi_mobile.retrofit.model.LoginResponse
import com.example.agrisynergi_mobile.retrofit.model.User
import com.example.agrisynergi_mobile.retrofit.model.UserRequest
import com.example.agrisynergi_mobile.retrofit.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("auth/users")
    suspend fun getUsers(): List<User>

//    @POST("auth/register")
    @POST("register")
    suspend fun registerUser(@Body userRequest: UserRequest): Response<UserResponse>

//    @POST("auth/login")
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("sawah")
    suspend fun getSawahByLokasi(@Query("lokasi") lokasi: String): Response<SawahResponse>

    // New endpoint to fetch all sawah
    @GET("sawah")
    suspend fun getSawahList(): Response<SawahResponse> // Fetch all sawah data

}
