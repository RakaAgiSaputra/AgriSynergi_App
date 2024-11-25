package com.example.agrisynergi_mobile.data

data class UserResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: List<UserData>,
    val pagination: Pagination
)

data class UserData(
    val id_user: Int,
    val nama: String,
    val no_hp: String,
    val alamat: String,
    val email: String,
    val katasandi: String,
    val role: String,
    val foto: String?
)

data class Pagination(
    val total: Int,
    val per_page: Int,
    val current_page: Int,
    val total_pages: Int
)

