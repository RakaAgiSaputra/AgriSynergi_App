package com.example.agrisynergi_mobile.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailSawah(
    val idLokasi: Int,
    val idSawah: Int,
    val nama: String,
    val luas: Double,
    val jenisTanah: String,
    val hasilPanen: String,
    val produksi: String,
    val deskripsi: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable