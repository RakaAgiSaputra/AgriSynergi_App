package com.example.agrisynergi_mobile.retrofit.testing

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.OtpViewModel
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth

class MainTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Initialize ViewModel
            val otpViewModel: OtpViewModel = viewModel()

            // Use LocalContext to get the current activity context
            val context = LocalContext.current
            val activity = context as? Activity

            // Check if activity is null
            if (activity != null) {
                OtpScreen(viewModel = otpViewModel, activity = activity)
            } else {
                Toast.makeText(context, "Activity context is not available", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val otpViewModel: OtpViewModel = viewModel()
    val context = LocalContext.current
    val activity = context as? Activity
    if (activity != null) {
        OtpScreen(viewModel = otpViewModel, activity = activity)
    }
}
