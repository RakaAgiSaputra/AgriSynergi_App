package com.example.agrisynergi_mobile.pages

import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.navigation.Screen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { Animatable(0f) }
    val isDarkMode = isSystemInDarkTheme()

    LaunchedEffect(
        key1 = true,
        block = {
            scale.animateTo(
                targetValue = 0.9f,
                animationSpec = tween(
                    durationMillis = 800
                )
            )
            delay(2000L)
            navController.navigate(Screen.OnBoarding1.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    )

    Surface(
        modifier = Modifier
//            .padding(16.dp)
//            .scale(scale.value)
            .fillMaxSize(),
        color = Color(0xFF5B8C51)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val image = if (isDarkMode) {
                R.drawable.iconagrisynergy
            } else {
                R.drawable.iconagrisynergy
            }
            Image(painter = painterResource(id = image), contentDescription = null, modifier = Modifier.size(300.dp))
        }
    }
}