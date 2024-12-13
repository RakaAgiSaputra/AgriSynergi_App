package com.example.agrisynergi_mobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AgrySynergiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inisialisasi aplikasi jika diperlukan
    }
}
