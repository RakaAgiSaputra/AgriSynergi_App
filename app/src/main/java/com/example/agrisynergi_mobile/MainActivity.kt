package com.example.agrisynergi_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.libraries.places.api.Places
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.ui.theme.Agrisynergi_MobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Places API with your API key
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyDq9gpB8vOgqZuxe3LeXL1NrKy6K38NQDA")
        }

        enableEdgeToEdge()

        setContent {
            Agrisynergi_MobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AgrisynergiApp(navController = navController)
                }
            }
        }
    }
}
