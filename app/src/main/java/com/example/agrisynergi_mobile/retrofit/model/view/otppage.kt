package com.example.agrisynergi_mobile.retrofit.model.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.navigation.Screen

@Composable
fun OTPVerificationScreen(navController: NavController) {
    OTPVerificationContent(navController = navController)
}

@Composable
fun OTPVerificationContent(navController: NavController) {
    var otpCode by remember { mutableStateOf(List(4) { "" }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF13382C))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "OTP VERIFICATION",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Masukkan kode verifikasi yang baru saja kami kirimkan ke\nAlamat Email anda",
            fontSize = 14.sp,
            color = Color.LightGray,
            lineHeight = 18.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        // OTP Input Fields
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            otpCode.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value,
                    onValueChange = {
                        if (it.length <= 1) {
                            otpCode = otpCode.toMutableList().apply { this[index] = it }
                        }
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .size(60.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate(Screen.NewPass.route) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF58805A),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "verifikasi".uppercase())
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Kirim ulang
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Tidak menerima kode?", color = Color.LightGray)
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = { /* Handle kirim ulang */ }) {
                Text(text = "Kirim ulang", color = Color(0xFF6FCF97))
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun OTPVerificationPreview() {
    OTPVerificationScreen(navController = rememberNavController())
}
