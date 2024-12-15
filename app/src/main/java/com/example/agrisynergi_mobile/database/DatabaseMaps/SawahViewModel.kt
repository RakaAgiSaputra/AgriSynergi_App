package com.example.agrisynergi_mobile.database.DatabaseMaps

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SawahViewModel @Inject constructor(
    private val repository: SawahRepository
) : ViewModel() {

    private val _sawahList = MutableStateFlow<List<Sawah>>(emptyList())
    val sawahList: StateFlow<List<Sawah>> = _sawahList

    private val _selectedSawah = MutableStateFlow<Sawah?>(null)
    val selectedSawah: StateFlow<Sawah?> = _selectedSawah

    init {
        fetchSawahData()
    }

    fun fetchSawahData() {
        viewModelScope.launch {
            try {
                val data = repository.getSawahData()
                _sawahList.value = data
                Log.d("SawahViewModel", "Fetched sawah data: $data")
            } catch (e: Exception) {
                Log.e("SawahViewModel", "Error fetching sawah data", e)
            }
        }
    }

    fun getSawahByLokasi(lokasi: String) {
        viewModelScope.launch {
            val sawah = _sawahList.value.find { it.lokasi == lokasi }
            if (sawah != null) {
                _selectedSawah.value = sawah
            } else {
                Log.w("SawahViewModel", "No sawah found for location: $lokasi")
            }
        }
    }
}
