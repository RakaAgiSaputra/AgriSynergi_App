package com.example.agrisynergi_mobile.data

data class Market(
    val id: Int,
    val hasImage: Boolean,
    val imageUrl: String?,
    val title: String,
    val description: String,
    val price: String,
    val productDetail: String,
)

