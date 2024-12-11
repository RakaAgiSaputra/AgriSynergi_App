package com.example.agrisynergi_mobile.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailAgenda(
    val id: Int = 0,
    val imagePath: String,
    val judul: String,
    val tanggal: String,
    val deskripsiAgenda: String,
    val jenis: String
) : Parcelable