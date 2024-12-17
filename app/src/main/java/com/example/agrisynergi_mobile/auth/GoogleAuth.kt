package com.example.agrisynergi_mobile.auth

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.navigation.Screen
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.SharedPreferenceManager
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.IOException

private val WEB_ID_CLIENT = "79716735029-f5ok3m4ihfpc65gagbi48p3ndlv8mlkn.apps.googleusercontent.com"

class AuthManageer(
    private val context: Context,
    private val auth: FirebaseAuth,
    private val navController: NavHostController,
    private val sharedPreferenceManager: SharedPreferenceManager
) {
    // Fungsi untuk login dengan Google menggunakan FirebaseAuth
    fun loginWithGoogle(scope: CoroutineScope, credentialManager: CredentialManager, isUserAgri: Boolean) {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(isUserAgri)
            .setServerClientId(WEB_ID_CLIENT)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        scope.launch {
            try {
                val result = credentialManager.getCredential(context, request)
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            sharedPreferenceManager.saveLoginStatus(true)
                            navController.navigate(Screen.Beranda.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> {
                        Toast.makeText(context, "Network error. Please check your connection.", Toast.LENGTH_SHORT).show()
                    }
                    is GoogleAuthException -> {
                        Toast.makeText(context, "Google authentication error. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
