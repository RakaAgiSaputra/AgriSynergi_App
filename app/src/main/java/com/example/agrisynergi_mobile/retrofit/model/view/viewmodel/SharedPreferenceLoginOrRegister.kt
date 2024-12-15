package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import android.content.Context
import android.content.SharedPreferences
import com.example.agrisynergi_mobile.retrofit.model.LoginRequest
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance.apiService

class SharedPreferenceManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("user_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("user_token", null)
    }

    fun saveLoginStatus(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun getLoginStatus(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun clearPreferences() {
        prefs.edit().clear().apply()
    }


    // Simpan data pengguna
    fun saveUserData(nama: String, email: String, provinsi: String) {
        prefs.edit()
            .putString("user_nama", nama)
            .putString("user_email", email)
            .putString("user_provinsi", provinsi)
            .apply()
    }

    // Ambil data pengguna
    fun getUserNama(): String? = prefs.getString("user_nama", "")
    fun getUserEmail(): String? = prefs.getString("user_email", "")
    fun getUserProvinsi(): String? = prefs.getString("user_provinsi", "")

}
