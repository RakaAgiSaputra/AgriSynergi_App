package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.agrisynergi_mobile.database.testDatabase.Api

class MarketViewModelFactory(private val api: Api) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarketViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}