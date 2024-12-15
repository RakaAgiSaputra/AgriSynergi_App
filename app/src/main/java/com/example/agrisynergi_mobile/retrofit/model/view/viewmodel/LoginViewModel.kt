package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrisynergi_mobile.auth.AuthManageer
import com.example.agrisynergi_mobile.retrofit.model.LoginRequest
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val sharedPreferenceManager: SharedPreferenceManager) : ViewModel() {
    private val _loginResult = mutableStateOf<String>("")
    val loginResult: State<String> = _loginResult

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun setLoginResult(message: String) {
        _loginResult.value = message
    }

    private fun setLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }


    // Fungsi login dengan Retrofit
    fun login(username: String, password: String) {
        viewModelScope.launch {
            setLoadingState(true)
            try {
                val request = LoginRequest(username, password)
                val response = RetrofitInstance.apiService.login(request)

                // Pastikan API merespons dengan status yang benar
                if (response.isSuccessful) {
                    _loginResult.value = "Login successful"
                    setLoadingState(false)
                    sharedPreferenceManager.saveLoginStatus(true)
                } else {
                    _loginResult.value = "Email or password is incorrect"
                    setLoadingState(false)
                }
            } catch (e: HttpException) {
                _loginResult.value = "Login failed: Username atau password salahCek endpoint api ya... ${e.message}"
//                _loginResult.value = "Username atau password salah"
                setLoadingState(false)
            } catch (e: Exception) {
                _loginResult.value = "Login failed: ${e.message}"
                setLoadingState(false)
            }
        }
    }
}