package com.example.agrisynergi_mobile.database.ModelKomunitas


data class KomunitasResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: List<Komunitas>,
    val pagination: Pagination,
    val timestamp: String,
    val errors: Any?
)

data class Komunitas(
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

data class Pagination(
    val total: Int,
    val per_page: Int,
    val current_page: Int,
    val total_pages: Int
)

