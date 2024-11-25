package com.example.agrisynergi_mobile.consultant

import android.net.Uri

data class UserMessage(
    val user: String,
    val type: String, // "text" atau "image"
    val message: String,
    val isUser: Boolean,
    val imageUri: Uri? = null // URI untuk gambar (opsional)
)
