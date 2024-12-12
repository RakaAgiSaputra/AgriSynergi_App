package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrisynergi_mobile.retrofit.model.LoginRequest
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {
    private val _loginResult = mutableStateOf<String>("")
    val loginResult: State<String> = _loginResult

    fun setLoginResult(message: String) {
        _loginResult.value = message
    }

    // Fungsi login dengan Retrofit
    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(username, password)
                val response = RetrofitInstance.apiService.login(request)
                _loginResult.value = "Login successful"
            } catch (e: HttpException) {
                _loginResult.value = "Login failed: ${e.message}"
            } catch (e: Exception) {
                _loginResult.value = "Login failed: ${e.message}"
            }
        }
    }
}