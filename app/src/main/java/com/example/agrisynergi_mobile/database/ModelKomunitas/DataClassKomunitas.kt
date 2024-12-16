package com.example.agrisynergi_mobile.database.ModelKomunitas

data class CommunityResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: List<CommunityData>
)

data class CommunityData(
    val id_komunitas: Int,
    val id_user: Int,
    val gambar: String,
    val deskripsi: String,
    val komentator: List<Komentator>
)

data class Komentator(
    val id_komentator: Int,
    val id_user: Int,
    val nama_user: String,
    val deskripsi: String?,
    val type: String?
)

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

