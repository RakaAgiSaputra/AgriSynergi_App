package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.agrisynergi_mobile.database.RetrofitClient1
import com.example.agrisynergi_mobile.database.testDatabase.Api
import com.example.agrisynergi_mobile.database.testDatabase.Produk
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarketViewModel(private val api: Api) : ViewModel() {
    private val _products = MutableStateFlow<List<Produk>>(emptyList())
    val products: StateFlow<List<Produk>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchProducts(api: Api) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    api.getProduk().execute()
                }
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        _products.value = body.data // Assuming ProdukResponse has a 'data' field
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

    fun getProductById(id: Int): Produk? {
        return _products.value.find { it.id_produk == id }
    }
}