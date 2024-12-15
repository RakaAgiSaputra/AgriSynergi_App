package com.example.agrisynergi_mobile.retrofit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class LoginRequest(val email: String, val katasandi: String)
data class LoginResponse(val message: String, val token: String, val success: Boolean)

@Parcelize
data class User(
    val id: Int,
    val username: String,
    val email: String
):Parcelable

@Parcelize
data class UserRequest(
    val username: String,
    val email: String,
    val katasandi: String,
    val address: String,
    val phone_number: String,
):Parcelable

@Parcelize
data class UserResponse(
    val id: Int,
    val username: String,
    val email: String,
    val katasandi: String,
    val message: String // Misalnya ada pesan status dari server
):Parcelable

//Login Request

//"id": 1,
//"username": "testuser",
//"email": "test@example.com",
//"password": "hashedpassword123",
//"created_at": "2024-12-10T23:24:25.000Z",
//"reset_token": null

// Register Request
//{
//    "username": "john_doe",
//    "email": "john@example.com",
//    "password": "securepassword123",
//    "address": "123 Main Street, Some City",
//    "phone_number": "+62123456789"
//}

