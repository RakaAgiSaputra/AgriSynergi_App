package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import android.util.Log
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
                    Log.d("API Response", "Response received: $response")

                    val fotoUrl = "https://gtk62vzp-3000.asse.devtunnels.ms/api/fileUsers/${response.data?.user?.foto ?: ""}"
                    Log.d("LoginViewModel", "Generated Foto URL: $fotoUrl")

                    sharedPreferenceManager.saveToken(response.data?.token ?: "")
                    sharedPreferenceManager.saveLoginStatus(true)
                    sharedPreferenceManager.saveUserData(
                        nama = response.data?.user?.nama ?: "",
                        email = response.data?.user?.email ?: "",
                        provinsi = response.data?.user?.provinsi ?: "",
                        no_hp = response.data?.user?.no_hp ?: "",
                        foto = fotoUrl,
                        kota = response.data?.user?.kota ?: "",
                        alamat = response.data?.user?.alamat ?: "",
                        kodepos = response.data?.user?.kodepos ?: "",
                        userId = response.data?.user?.id_user ?: 0,
                        katasandi = response.data?.user?.katasandi ?: "",
                    )

                    _loginResult.value = "Login successful"
                } else {
                    _loginResult.value = response.message
                }
            } catch (e: HttpException) {
                Log.e("LoginViewModel", "HTTP Exception: ${e.message()}")
                _loginResult.value = "Login failed: ${e.message()}"
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception: ${e.message}")
                _loginResult.value = "Login failed: ${e.message}"
            } finally {
                setLoadingState(false)
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}

