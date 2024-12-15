package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrisynergi_mobile.retrofit.model.LoginRequest
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val sharedPreferenceManager: SharedPreferenceManager) : ViewModel() {
    private val _loginResult = mutableStateOf<String>("")
    val loginResult: State<String> = _loginResult

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            setLoadingState(true)
            try {
                val request = LoginRequest(email, password)
                val response = RetrofitInstance.apiService.login(request)

                if (response.success) {
                    _loginResult.value = "Login successful"
                    // Simpan token dan user detail jika login berhasil
                    val userData = response.data?.user
                    sharedPreferenceManager.saveToken(response.data?.token ?: "")
                    sharedPreferenceManager.saveLoginStatus(true)
                    sharedPreferenceManager.saveUserData(
                        nama = userData?.nama ?: "Pengguna",
                        email = userData?.email ?: "user@gmail.com",
                        provinsi = userData?.provinsi ?: "Jawa Timur"
                    )
                    setLoadingState(false)
                } else {
                    _loginResult.value = response.message
                    setLoadingState(false)
                }
            } catch (e: HttpException) {
                _loginResult.value = "Login failed: ${e.message()}"
                setLoadingState(false)
            } catch (e: Exception) {
                _loginResult.value = "Login failed: ${e.message}"
                setLoadingState(false)
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}

