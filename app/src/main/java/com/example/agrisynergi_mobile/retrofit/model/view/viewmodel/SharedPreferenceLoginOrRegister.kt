package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.auth.AuthManageer
import com.example.agrisynergi_mobile.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

class SharedPreferenceManager(
    private val context: Context
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    // Fungsi untuk menyimpan status login di SharedPreferences
    fun saveLoginStatus(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()  // Apply for async saving
    }

    // Fungsi untuk mengambil status login dari SharedPreferences
    fun getLoginStatus(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    // Fungsi untuk logout dan menghapus status login dari SharedPreferences
    fun logout() {
        val editor = sharedPreferences.edit()
        editor.remove("isLoggedIn")
        editor.apply()
    }
}