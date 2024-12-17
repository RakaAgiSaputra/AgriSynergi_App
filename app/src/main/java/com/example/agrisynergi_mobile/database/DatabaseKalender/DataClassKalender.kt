package com.example.agrisynergi_mobile.database.DatabaseKalender

import com.google.gson.annotations.SerializedName

data class KalenderResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Kalender>,
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("errors") val errors: Any?
)

data class Kalender(
    val id_kalender: Int,
    val id_user: Int,
    val jenis: String,
    val judul: String,
    val tanggal: String,
    val deskripsi: String,
    val gambar: String,
)

data class KalenderRequest(
    val id_user: Int,
    val jenis: String,
    val judul: String,
    val tanggal: String,
    val deskripsi: String,
    val gambar: String
)

data class Pagination(
    val total: Int,
    val per_page: Int,
    val current_page: Int,
    val total_pages: Int
)