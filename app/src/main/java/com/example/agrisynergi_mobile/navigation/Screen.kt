package com.example.agrisynergi_mobile.navigation

sealed class Screen(val route: String) {
    object Beranda : Screen("beranda")
    object Maps : Screen("maps")
    object Market : Screen("market")
    object Konsultasi : Screen("konsultasi")
    object User : Screen("user")
    object Forum : Screen("forum")
    object DetailMarket : Screen("detailmarket/{marketId}")
}
