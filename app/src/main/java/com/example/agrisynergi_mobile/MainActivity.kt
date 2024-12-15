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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance
import com.example.agrisynergi_mobile.ui.theme.Agrisynergi_MobileTheme
import com.example.agrisynergi_mobile.database.DatabaseMaps.SawahViewModel

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyBMv4uLFCw4dP2l2q3lg6oRZC0LPCQ20xk")
        }


        enableEdgeToEdge()
        auth = Firebase.auth


        setContent {
            Agrisynergi_MobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {val context = LocalContext.current
                    val navController = rememberNavController()
                    AgrisynergiApp(navController = navController, auth = auth)
                }
            }
        }
    }
}
