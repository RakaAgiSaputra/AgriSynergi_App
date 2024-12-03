package com.example.agrisynergi_mobile.database.DatabaseRegister

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: Any?,
    val pagination: Any?,
    val timestamp: String,
    val errors: Errors?
)

data class Errors(
    val missingFields: List<String>
)

data class UserData(
    @SerializedName("nama") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("no_hp") val phoneNumber: String?,
    @SerializedName("katasandi") val password: String?,
    @SerializedName("alamat") val address: String?
)

