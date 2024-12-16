package com.example.agrisynergi_mobile.retrofit.model.view.viewmodel

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import java.util.concurrent.TimeUnit

class OtpViewModel : ViewModel() {
    var verificationId = mutableStateOf("")
    var otpSent = mutableStateOf(false)
    var isVerifying = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Mengirim OTP ke nomor telepon
    fun sendOtpToPhoneNumber(phoneNumber: String, activity: Activity, onOtpSent: (String) -> Unit) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Nomor telepon yang ingin diverifikasi
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout verifikasi
            .setActivity(activity) // Aktivitas untuk callback
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Verifikasi selesai secara otomatis (misalnya OTP ditemukan)
                    signInWithPhoneAuthCredential(credential,activity)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // Tangani kesalahan verifikasi
                    errorMessage.value = "Verifikasi gagal: ${e.localizedMessage ?: e.message}"
                    Log.e("OTP", "Verification failed: ${e.localizedMessage}")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    // OTP berhasil dikirim
                    this@OtpViewModel.verificationId.value = verificationId
                    otpSent.value = true
                    onOtpSent(verificationId)
                    Log.d("OTP", "Code sent: $verificationId")
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // Verifikasi OTP yang dimasukkan pengguna
    fun verifyOtp(otp: String, activity: Activity) {
        isVerifying.value = true
        val credential = PhoneAuthProvider.getCredential(verificationId.value, otp)
        signInWithPhoneAuthCredential(credential,activity)
    }

    // Menyelesaikan login setelah OTP berhasil diverifikasi
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, activity: Activity) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                isVerifying.value = false
                if (task.isSuccessful) {
                    // Pengguna berhasil login
                    val user = task.result?.user
                    Log.d("OTP", "Login successful: ${user?.phoneNumber}")
                    // Arahkan ke layar utama
                    Toast.makeText(activity, "Rawrrrr", Toast.LENGTH_SHORT).show()
                } else {
                    // Tangani kesalahan jika login gagal
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        errorMessage.value = "OTP tidak valid"
                    } else {
                        errorMessage.value = "Login gagal: ${task.exception?.localizedMessage}"
                    }
                }
            }
    }
}
