package com.example.agrisynergi_mobile.database.ModelKomunitas

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrisynergi_mobile.retrofit.model.User
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance.apiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _users = mutableStateListOf<User>()
    val users: List<User> get() = _users

    fun getUsers() {
        viewModelScope.launch {
            try {
                val response = apiService.getUsers()

                if (response.success) {
                    // Akses data users
                    val users = response.data
                    // Lakukan sesuatu dengan data users
                    _users.clear()
                    _users.addAll(users)
                    println("Fetched users: $users")
                } else {
                    // Tangani kasus jika success false
                    println("Failed to fetch users: ${response.message}")
                }
            } catch (e: Exception) {
                println("Error fetching users: ${e.message}")
            }
        }
    }

}
