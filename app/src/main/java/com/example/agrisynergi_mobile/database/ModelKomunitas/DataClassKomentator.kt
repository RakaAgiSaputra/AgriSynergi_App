package com.example.agrisynergi_mobile.database.ModelKomunitas

data class Komentator(
    val id_komentator: Int,
    val id_user: Int,
    val id_komunitas: Int,
    val deskripsi: String?,
    val type: String?
)

data class KomentatorResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: List<Komentator>,
    val pagination: Pagination
)

data class Pagination(
    val total: Int,
    val per_page: Int,
    val current_page: Int,
    val total_pages: Int
)

data class KomentarRequest(
    val id_user: Int,
    val id_komunitas: Int,
    val deskripsi: String
)

