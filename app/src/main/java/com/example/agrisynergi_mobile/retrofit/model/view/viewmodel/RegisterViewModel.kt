package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrisynergi_mobile.retrofit.model.User
import com.example.agrisynergi_mobile.retrofit.model.UserRequest
import com.example.agrisynergi_mobile.retrofit.model.UserResponse
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _users = mutableStateListOf<User>()
    val users: List<User> get() = _users

    // Menambahkan properti untuk menyimpan hasil login
    private val _registerResult = mutableStateOf("")
    val registerResult: State<String> get() = _registerResult

    // Fungsi untuk mendaftar pengguna
    fun registerUser(nama: String, email: String, katasandi: String, alamat: String, no_hp: String) {
        val userRequest = UserRequest(nama, email, katasandi, alamat, no_hp)

        viewModelScope.launch {
            try {
                val response: Response<UserResponse> = apiService.registerUser(userRequest)
                if (response.isSuccessful) {
                    // Update registerResult jika sukses
                    _registerResult.value = "Registration successful"
                } else {
                    // Update registerResult jika gagal
                    _registerResult.value = "Registration failed: ${response.message()}"
                }
            } catch (e: Exception) {
                // Handle error, misalnya jaringan tidak tersedia
                _registerResult.value = "Registration failed: ${e.message}"
            }
        }
    }


    // test untuk mendapatkan data user
//    fun getUsers() {
//        viewModelScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) { RetrofitInstance.apiService.getUsers() }
//                _users.clear()
//                _users.addAll(response)
//            } catch (e: Exception) {
//                // Handle error
//            }
//        }
//    }
    fun getUsers() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { RetrofitInstance.apiService.getUsers() }
                if (response.success) {
                    _users.clear()
                    _users.addAll(response.data)
                } else {
                    println("Error: ${response.message}")
                }
            } catch (e: Exception) {
                println("Error fetching users: ${e.message}")
            }
        }
    }

}
