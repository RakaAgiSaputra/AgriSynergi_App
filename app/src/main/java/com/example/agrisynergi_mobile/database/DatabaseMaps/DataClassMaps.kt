package com.example.agrisynergi_mobile.database.DatabaseMaps

data class SawahResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: List<Sawah>,
    val pagination: Pagination
)

data class Sawah(
    val id_sawah: Int,
    val id_user: Int,
    val lokasi: String,
    val luas: String,
    val foto_lokasi: String,
    val id_lokasi: Int,
    val jenis_tanah: String,
    val hasil_panen: String,
    val produksi: String,
    val deskripsi: String,
    val latitude: String,
    val longitude: String
)

data class Pagination(
    val total: Int,
    val per_page: Int,
    val current_page: Int,
    val total_pages: Int
)
sealed class SawahUiState {
    object Loading : SawahUiState()
    data class Success(val data: List<Sawah>) : SawahUiState()
    data class Error(val message: String) : SawahUiState()
    object Empty : SawahUiState() // Untuk menangani data kosong
}


