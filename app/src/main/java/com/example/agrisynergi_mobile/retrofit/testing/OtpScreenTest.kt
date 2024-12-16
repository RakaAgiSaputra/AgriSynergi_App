package com.example.agrisynergi_mobile.retrofit.testing

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.OtpViewModel

@Composable
fun OtpScreen(viewModel: OtpViewModel, activity: Activity) {
    // State untuk nomor telepon dan OTP
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }

    // Menampilkan status pengiriman OTP dan verifikasi OTP
    val otpSent by viewModel.otpSent
    val isVerifying by viewModel.isVerifying
    val errorMessage by viewModel.errorMessage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Input nomor telepon
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            isError = phoneNumber.isEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol Kirim OTP
        Button(
            onClick = {
                if (phoneNumber.isNotEmpty()) {
                    viewModel.sendOtpToPhoneNumber(phoneNumber, activity) { verificationId ->
                        // OTP berhasil dikirim, kamu bisa menyimpan verificationId untuk digunakan saat verifikasi
                    }
                } else {
                    Toast.makeText(activity, "Nomor telepon tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = phoneNumber.isNotEmpty() && !otpSent
        ) {
            Text(text = "Kirim OTP")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menampilkan input OTP jika OTP sudah dikirim
        if (otpSent) {
            TextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("Masukkan OTP") },
                modifier = Modifier.fillMaxWidth(),
                isError = otp.isEmpty()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Verifikasi OTP
            Button(
                onClick = {
                    if (otp.isNotEmpty()) {
                        viewModel.verifyOtp(otp,activity)
                    } else {
                        Toast.makeText(activity, "OTP tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = otp.isNotEmpty() && !isVerifying
            ) {
                Text(text = "Verifikasi OTP")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menampilkan pesan kesalahan jika ada
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }

        // Menampilkan loading indicator saat OTP sedang diverifikasi
        if (isVerifying) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOtpScreen() {
    val viewModel = OtpViewModel() // Gunakan ViewModel yang sesuai
    OtpScreen(viewModel = viewModel, activity = Activity()) // Gantilah Activity dengan activity yang relevan
}
