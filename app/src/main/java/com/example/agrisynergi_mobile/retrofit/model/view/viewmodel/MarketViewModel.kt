package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.agrisynergi_mobile.database.RetrofitClient
import com.example.agrisynergi_mobile.database.testDatabase.Produk
import androidx.compose.foundation.layout.*
import com.example.agrisynergi_mobile.database.DatabaseProduk.ProdukResponse

class MarketViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Produk>>(emptyList())
    val products: StateFlow<List<Produk>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.apiProduk.getProduk()

                if (response.isSuccessful()) {
                    val produkResponse = response.body()
                    produkResponse?.let {
                        if (it.success) {
                            _products.value = it.data
                            _error.value = null
                        } else {
                            _error.value = it.message
                        }
                    }
                } else {
                    _error.value = "Failed to fetch products"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}