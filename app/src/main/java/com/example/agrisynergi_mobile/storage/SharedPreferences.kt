package com.example.agrisynergi_mobile.storage

import android.content.Context

class SharedPrefManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("AgrisynergiPref", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    // Simpan token
    fun saveToken(token: String) {
        editor.putString("USER_TOKEN", token)
        editor.apply()
    }

    // Ambil token
    fun getToken(): String? {
        return sharedPreferences.getString("USER_TOKEN", null)
    }

    // Hapus token
    fun clearToken() {
        editor.remove("USER_TOKEN").apply()
    }
}
