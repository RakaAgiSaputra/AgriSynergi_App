package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.agrisynergi_mobile.retrofit.model.LoginRequest
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance.apiService

class SharedPreferenceManager(context: Context) {
    val prefs: SharedPreferences =
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

    fun saveUserData(
        nama: String,
        email: String,
        katasandi: String,
        provinsi: String,
        no_hp: String,
        foto: String,
        kota: String,
        alamat: String,
        kodepos: String,
        userId: Int
    ) {
        val editor = prefs.edit()
        editor.putString("user_nama", nama)
        editor.putString("user_email", email)
        editor.putString("user_katasandi", katasandi)
        editor.putString("user_provinsi", provinsi)
        editor.putString("user_no_hp", no_hp)
        editor.putString("user_foto", foto)
        editor.putString("user_kota", kota)
        editor.putString("user_alamat", alamat)
        editor.putString("user_kodepos", kodepos)
        editor.putInt("user_id", userId)
        editor.apply()
    }

    fun getUserId(): Int = prefs.getInt("user_id", -1)
    fun getUserNama(): String? = prefs.getString("user_nama", "")
    fun getUserEmail(): String? = prefs.getString("user_email", "")
    fun getUserProvinsi(): String? = prefs.getString("user_provinsi", "")
    fun getUserFoto(): String? = prefs.getString("user_foto", "")
    fun getPassword(): String? = prefs.getString("user_password", "")

}
