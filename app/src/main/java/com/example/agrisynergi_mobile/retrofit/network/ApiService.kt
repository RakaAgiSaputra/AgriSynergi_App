package com.example.agrisynergi_mobile.retrofit.network

import com.example.agrisynergi_mobile.retrofit.model.LoginRequest
import com.example.agrisynergi_mobile.retrofit.model.LoginResponse
import com.example.agrisynergi_mobile.retrofit.model.User
import com.example.agrisynergi_mobile.retrofit.model.UserRequest
import com.example.agrisynergi_mobile.retrofit.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("auth/users")
    suspend fun getUsers(): List<User>

    @POST("auth/register")
    suspend fun registerUser(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

}
