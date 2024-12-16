package com.example.agrisynergi_mobile.User

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.onFocusChanged
import androidx.lifecycle.lifecycleScope

import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.retrofit.model.User
import com.example.agrisynergi_mobile.retrofit.model.UserRequest
import com.example.agrisynergi_mobile.retrofit.model.UserResponse
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.SharedPreferenceManager
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : ComponentActivity() {
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferenceManager = SharedPreferenceManager(this)

        val userData = sharedPreferenceManager.run {
            User(
                id_user = getUserId(),
                nama = getUserNama() ?: "",
                no_hp = prefs.getString("user_no_hp", "") ?: "",
                alamat = prefs.getString("user_alamat", "") ?: "",
                email = getUserEmail() ?: "",
                role = "",
                foto = getUserFoto() ?: "",
                provinsi = getUserProvinsi() ?: "",
                kota = prefs.getString("user_kota", "") ?: "",
                kodepos = prefs.getString("user_kodepos", "") ?: "",
                katasandi = getPassword() ?: "",
            )
        }

        setContent {
            EditProfileScreen(
                userData = userData,
                onBackPressed = { finish() },
                onSaveProfile = { updatedUser ->
                    saveProfileToServer(updatedUser)
                }
            )
        }
    }

    private fun saveProfileToServer(user: User) {
        val userId = sharedPreferenceManager.getUserId()
        if (userId <= 0) {
            showToast("User ID tidak valid")
            return
        }

        val request = UserRequest(
            nama = user.nama,
            email = user.email,
            katasandi = sharedPreferenceManager.getPassword() ?: "",
            alamat = user.alamat,
            no_hp = user.no_hp
        )

        RetrofitInstance.apiService.updateUserProfile(userId, request).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    handleResponse(response, user)
                } else {
                    Log.e("EditProfile", "Gagal memperbarui: ${response.errorBody()?.string()}")
                    showToast("Gagal memperbarui profil")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("EditProfile", "Error: ${t.message}")
                showToast("Kesalahan jaringan: ${t.message}")
            }
        })
    }


    private fun handleResponse(response: Response<UserResponse>, user: User) {
        if (response.isSuccessful && response.body()?.success == true) {
            sharedPreferenceManager.saveUserData(
                nama = user.nama,
                email = user.email,
                alamat = user.alamat,
                foto = sharedPreferenceManager.getUserFoto() ?: "",
                no_hp = user.no_hp,
                provinsi = user.provinsi,
                kota = user.kota,
                kodepos = user.kodepos,
                userId = user.id_user,
                katasandi = user.katasandi
            )
            showToast("Profil berhasil diperbarui")
        } else {
            Log.e("EditProfile", "Gagal memperbarui: ${response.errorBody()?.string()}")
            showToast("Gagal memperbarui profil")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun EditProfileScreen(
    userData: User,
    onBackPressed: () -> Unit,
    onSaveProfile: (User) -> Unit
) {
    var user by remember { mutableStateOf(userData) }
    var isLoading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            EditProfileHeader(onBackPressed)

            Column(modifier = Modifier.padding(16.dp)) {
                TextFieldWithLabel("Nama", user.nama) { user = user.copy(nama = it) }
                TextFieldWithLabel("Email", user.email) { user = user.copy(email = it) }
                TextFieldWithLabel("Nomor HP", user.no_hp) { user = user.copy(no_hp = it) }
                TextFieldWithLabel("Alamat", user.alamat) { user = user.copy(alamat = it) }
                TextFieldWithLabel("Kota", user.kota) { user = user.copy(kota = it) }
                TextFieldWithLabel("Provinsi", user.provinsi) { user = user.copy(provinsi = it) }
                TextFieldWithLabel("Kodepos", user.kodepos) { user = user.copy(kodepos = it) }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        isLoading = true
                        onSaveProfile(user)
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5F897B),
                        contentColor = Color.White
                    )
                ) {
                    Text("Simpan")
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun TextFieldWithLabel(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(label, color = Color.Black)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF5F897B),
                unfocusedIndicatorColor = Color.Gray
            )
        )
    }
}
@Composable
fun EditProfileHeader(onBackPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back Arrow",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .clickable { onBackPressed() }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Edit Profile",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
fun FocusedOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeholder: @Composable (() -> Unit)? = null,
    textColor: Color = Color.Black
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState -> isFocused = focusState.isFocused },
        visualTransformation = visualTransformation,
        placeholder = if (value.isEmpty()) placeholder else null,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (isFocused) Color(0xFFBCD7BC) else Color.Transparent,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedIndicatorColor = Color(0xFF00A305),
            unfocusedIndicatorColor = Color(0xFF5F897B),
        )
    )
}