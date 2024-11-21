package com.example.agrisynergi_mobile.data


data class Forum(
    val id: Int,
    val user: User,
    val hasImage: Boolean,
    val imageUrl: String?,
    val numComment: Int,
    val numLikes: Int,
    val numMarkah: Int,
    val date: String,
    val text: String,
    )
