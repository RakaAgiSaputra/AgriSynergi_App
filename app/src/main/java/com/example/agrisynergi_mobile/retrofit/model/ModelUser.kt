package com.example.agrisynergi_mobile.retrofit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserRequest(
    val nama: String,
    val password: String,
    val alamat: String,
    val katasandi: String,
    val no_hp: String
)

// Response untuk User List
data class UserResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: List<User>
)

data class LoginRequest(
    val email: String,
    val katasandi: String
)

data class LoginResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: UserData?
)

data class UserData(
    val token: String,
    val user: User
)

// Model untuk response API
data class ApiResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: List<User>, // Menyimpan array data users
    val pagination: Pagination?,
    val timestamp: String,
    val errors: Any?
)

// Model Pagination (opsional)
data class Pagination(
    val total: Int,
    val per_page: Int,
    val current_page: Int,
    val total_pages: Int
)
data class User(
    val id_user: Int,
    val nama: String,
    val no_hp: String,
    val alamat: String,
    val email: String,
    val katasandi: String,
    val role: String,
    val foto: String,
    val provinsi: String?,
    val kota: String?,
    val kodepos: String?
)



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

