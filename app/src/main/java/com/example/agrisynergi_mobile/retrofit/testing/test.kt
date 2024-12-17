package com.example.agrisynergi_mobile.retrofit.testing

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agrisynergi_mobile.navigation.Screen
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.LoginViewModel

@Composable
fun LoginccScreen(navController: NavController, viewModel: LoginViewModel = viewModel()) {
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    // Context untuk menampilkan Toast
    val context = LocalContext.current

    // Mengamati perubahan loginResult untuk menampilkan Toast jika login berhasil
    LaunchedEffect(viewModel.loginResult.value) {
        if (viewModel.loginResult.value == "Login successful") {
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Konsultasi.route)
        } else if (viewModel.loginResult.value.startsWith("Login failed")) {
            Toast.makeText(context, viewModel.loginResult.value, Toast.LENGTH_SHORT).show()
        }
    }

    // UI untuk input username dan password
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Login")
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") }
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") }
        )

        Button(onClick = {
            // Validasi input sebelum login
            if (username.value.isNotBlank() && password.value.isNotBlank()) {
                viewModel.login(username.value, password.value)
            } else {
                Toast.makeText(context, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Login")
        }

        // Menampilkan hasil dari login
        Text(text = viewModel.loginResult.value)
    }
}