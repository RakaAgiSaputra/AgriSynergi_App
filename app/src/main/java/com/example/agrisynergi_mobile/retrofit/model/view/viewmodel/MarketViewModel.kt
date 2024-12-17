package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.agrisynergi_mobile.database.testDatabase.Api
import com.example.agrisynergi_mobile.database.testDatabase.Produk
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

    fun addToCart(productId: Int, userId: Int, quantity: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = api.addToCart(productId, userId, quantity)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        if (body.success) {
                            // Refresh the cart after successful addition
                            refreshCartProducts()
                            _error.value = null
                        } else {
                            _error.value = "Failed to add to cart: ${body.message}"
                        }
                    } ?: run {
                        _error.value = "Empty response from server"
                    }
                } else {
                    _error.value = "Failed to add to cart: ${response.errorBody()?.string() ?: response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun refreshCartProducts() {
        // Implementasikan logika untuk memperbarui daftar produk di keranjang
        // Misalnya, Anda dapat memanggil API untuk mendapatkan daftar produk terbaru di keranjang
        // dan memperbarui nilai _products dengan data yang sesuai
        viewModelScope.launch {
            try {
                val cartResponse = api.getKeranjang()
                if (cartResponse.isSuccessful) {
                    cartResponse.body()?.let { cartBody ->
                        if (cartBody.success) {
                            _products.value = cartBody.data.mapNotNull { keranjang ->
                                getProductById(keranjang.id_produk)
                            }
                        } else {
                            _error.value = cartBody.message
                        }
                    } ?: run {
                        _error.value = "Data keranjang kosong"
                    }
                } else {
                    _error.value = "Failed to fetch cart products: ${cartResponse.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

    fun getProductById(id: Int): Produk? {
        return _products.value.find { it.id_produk == id }
    }
}