package com.example.agrisynergi_mobile.utils

import com.example.agrisynergi_mobile.navigation.Screen

//Kode untuk memberikan navigasi bootm bar pada screen
fun String?.shouldShowBottomBar(): Boolean {
    return this in setOf(
        Screen.Beranda.route,
    )
}