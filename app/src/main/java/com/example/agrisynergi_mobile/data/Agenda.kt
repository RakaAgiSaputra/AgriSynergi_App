package com.example.agrisynergi_mobile.data

data class Agenda(
    val id: Int = 0,
    val imagePath: String,
    val judul: String,
    val deskripsiAgenda: String,
    val tanggal: String, // Timestamp format
    val jenis: String // Misalnya: "Reminder", "Event"
)
