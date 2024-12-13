package com.example.agrisynergi_mobile.database

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrisynergi_mobile.database.DatabaseMaps.SawahResponse
import com.example.agrisynergi_mobile.database.DatabaseMaps.Sawah
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SawahViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    // StateFlow untuk menyimpan daftar sawah yang diterima
    private val _sawahList = MutableStateFlow<List<Sawah>>(emptyList())
    val sawahList: StateFlow<List<Sawah>> get() = _sawahList

    // StateFlow untuk menyimpan sawah yang dipilih
    private val _selectedSawah = MutableStateFlow<Sawah?>(null)
    val selectedSawah: StateFlow<Sawah?> get() = _selectedSawah

    // Fungsi untuk mengambil data sawah dari API
    fun getSawahList() {
        viewModelScope.launch {
            try {
                val response = apiService.getSawahList()
                if (response.isSuccessful) {
                    _sawahList.value = response.body()?.data ?: emptyList()
                } else {
                    Log.e("SawahViewModel", "Error fetching data: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("SawahViewModel", "Exception: ${e.message}")
            }
        }
    }

    // Fungsi untuk mengambil data sawah berdasarkan lokasi
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

