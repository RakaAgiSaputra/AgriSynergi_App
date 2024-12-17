package com.example.agrisynergi_mobile.database.ModelKomunitas

import com.google.gson.annotations.SerializedName

data class CommunityResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: List<CommunityData>
)

data class CommunityData(
    @SerializedName("id_komunitas") val idKomunitas: Int, // Di sini memakai idKomunitas di Kotlin
    @SerializedName("id_user") val idUser: Int,
    val gambar: String?,
    val deskripsi: String,
    val komentator: List<Komentator>
)

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Result<Nothing>()

}

