package com.example.agrisynergi_mobile.database

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrisynergi_mobile.database.DatabaseMaps.SawahResponse
import com.example.agrisynergi_mobile.database.DatabaseMaps.Sawah
import com.example.agrisynergi_mobile.database.DatabaseMaps.SawahUiState
import com.example.agrisynergi_mobile.retrofit.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SawahViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _sawahList = MutableStateFlow<SawahUiState>(SawahUiState.Loading)
    val sawahList: StateFlow<SawahUiState> = _sawahList.asStateFlow()

    private val _selectedSawah = MutableStateFlow<Sawah?>(null)
    val selectedSawah: StateFlow<Sawah?> get() = _selectedSawah

    fun getSawahList() {
        viewModelScope.launch {
            try {
                val response = apiService.getSawahList()
                if (response.isSuccessful) {
                    val sawahData = response.body()?.data ?: emptyList()
                    _sawahList.value = SawahUiState.Success(sawahData)
                } else {
                    _sawahList.value = SawahUiState.Error("Error fetching data: ${response.errorBody()?.string()}")
                    Log.e("SawahViewModel", "Error fetching data: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _sawahList.value = SawahUiState.Error("Exception: ${e.message}")
                Log.e("SawahViewModel", "Exception: ${e.message}")
            }
        }
    }

    // Get sawah by location
    fun getSawahByLokasi(lokasi: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getSawahByLokasi(lokasi)
                if (response.isSuccessful) {
                    _selectedSawah.value = response.body()?.data?.firstOrNull()
                } else {
                    Log.e("SawahViewModel", "Error fetching data: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("SawahViewModel", "Exception: ${e.message}")
            }
        }
    }
}


