package com.example.agrisynergi_mobile.navigation

sealed class Screen(val route: String) {
    object Beranda : Screen("beranda")
    object Maps : Screen("maps")
    object Market : Screen("market")
    object Konsultasi : Screen("konsultasi")
    object User : Screen("user")
    object Forum : Screen("forum")
    object DetailMarket : Screen("detailmarket/{marketId}")
    object Notifikasi : Screen("notifikasi")
    data object Splash: Screen("splash")
    data object OnBoarding1 : Screen("onboarding1")
    data object OnBoarding2 : Screen("onboarding2")
    data object OnBoarding3 : Screen("onboarding3")
    data object OnBoarding4 : Screen("onboarding4")
    data object OnBoarding5 : Screen("onboarding5")
}
