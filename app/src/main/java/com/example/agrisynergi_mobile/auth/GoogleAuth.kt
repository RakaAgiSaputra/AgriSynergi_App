package com.example.agrisynergi_mobile.auth

import android.content.Context
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.navigation.Screen
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.IOException

private val WEB_ID_CLIENT = "79716735029-f5ok3m4ihfpc65gagbi48p3ndlv8mlkn.apps.googleusercontent.com"

fun AuthWithGoogle(scope: CoroutineScope,context: Context,credentialManager: CredentialManager,auth: FirebaseAuth,navController: NavHostController, isUserAgri: Boolean ){
    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(isUserAgri) // Filter akun yang udah terotorisasi aja
        .setServerClientId(com.example.agrisynergi_mobile.auth.WEB_ID_CLIENT)  //  client ID
        .build()

    // Request untuk ambil kredensial dari Credential Manager
    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    scope.launch { // Jalankan di coroutine biar nggak ngeblok thread utama
        try {
            // Ambil kredensial dari Credential Manager
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            // Ambil Google ID Token dari kredensial yang didapet
            val credential = result.credential
            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(credential.data)

            val googleIdToken = googleIdTokenCredential.idToken

            // Login ke Firebase pake Google ID Token
            val firebaseCredential =
                GoogleAuthProvider.getCredential(googleIdToken, null)

            auth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Kalau login sukses, langsung ke halaman utama (Beranda)
                        navController.popBackStack() // Balik ke screen sebelumnya
                        navController.navigate(Screen.Beranda.route) // Pindah ke Beranda
                    }else{
                        Toast.makeText(
                            context,
                            "Daftar terlebih dahulu: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    // Handle error jaringan
                    Toast.makeText(
                        context,
                        "Terjadi kesalahan koneksi. Silakan periksa koneksi internet Anda.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is GoogleAuthException -> {
                    // Handle error autentikasi Google
                    Toast.makeText(
                        context,
                        "Terjadi kesalahan saat autentikasi dengan Google. Silakan coba lagi.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    // Handle error lainnya
                    Toast.makeText(
                        context,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            e.printStackTrace()
        }
    }
}
