package com.example.agrisynergi_mobile.database.DatabaseKalender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.agrisynergi_mobile.database.testDatabase.Api
import com.example.agrisynergi_mobile.database.DatabaseKalender.Kalender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KalenderViewModel(private val api: Api) : ViewModel() {
    private val _kalender = MutableStateFlow<List<Kalender>>(emptyList())
    val kalender: StateFlow<List<Kalender>> = _kalender

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchCalendars() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    api.getKalender().execute()
                }
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        _kalender.value = body.data // Assuming KalenderResponse has a 'data' field
                    } ?: run {
                        _error.value = "Data kosong"
                    }
                } else {
                    _error.value = "Failed to fetch data: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createKalender(kalenderRequest: KalenderRequest) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = withContext(Dispatchers.IO) {
                    api.addKalender(Kalender(
                        id_kalender = 0, // This will be assigned by the server
                        id_user = kalenderRequest.id_user,
                        jenis = kalenderRequest.jenis,
                        judul = kalenderRequest.judul,
                        tanggal = kalenderRequest.tanggal,
                        deskripsi = kalenderRequest.deskripsi,
                        gambar = kalenderRequest.gambar
                    )).execute()
                }

                if (response.isSuccessful) {
                    _error.value = null
                    fetchCalendars() // Refresh the calendar list
                } else {
                    _error.value = "Failed to create calendar: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error creating calendar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getCalendarById(id: Int): Kalender? {
        return _kalender.value.find { it.id_kalender == id }
    }
}