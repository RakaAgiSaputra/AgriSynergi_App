package com.example.agrisynergi_mobile.navigation

sealed class Screen(val route: String) {
    object Beranda : Screen("beranda")
    object Agenda : Screen("agenda")
    object LoginRegist: Screen("loginregist")
    object Login : Screen("login")
    object LoginWithGoogle : Screen("loginwithgoogle")
    object Otp : Screen("otp")
    object ForgetPass : Screen("forgetpass")
    object NewPass : Screen("newpass")
    object Regist: Screen("regist")
    object Maps : Screen("maps")
    object Market : Screen("market")
    object Toko : Screen("toko")
    object DetailToko : Screen("detailtoko")
    object Pengiriman : Screen("pengiriman")
    object Counter : Screen("counter")
    object Pickup : Screen("pickup")
    object Konsultasi : Screen("konsultasi")
    object User : Screen("user")
    object Forum : Screen("forum")
    object AddPost : Screen("addpost")
    object DetailMarket : Screen("detailmarket/{marketId}")
    object Keranjang : Screen("keranjang/{marketId}") {
        fun createRoute(marketId: Int) = "keranjang/$marketId"
    }
    object Checkout : Screen("checkout/{marketId}") {
        fun createRoute(marketId: Int) = "checkout/$marketId"
    }
    object BeliSekarang : Screen("belisekarang/{marketId}") {
        fun createRoute(marketId: Int) = "belisekarang/$marketId"
    }

    object Notifikasi : Screen("notifikasi")
    data object Splash: Screen("splash")
    data object OnBoarding1 : Screen("onboarding1")
    data object OnBoarding2 : Screen("onboarding2")
    data object OnBoarding3 : Screen("onboarding3")
    data object OnBoarding4 : Screen("onboarding4")
    data object OnBoarding5 : Screen("onboarding5")
    object Komunitas : Screen("komunitas")
}
