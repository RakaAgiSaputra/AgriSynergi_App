package com.example.agrisynergi_mobile.retrofit.model.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.database.DatabaseRegister.UserResponse
import com.example.agrisynergi_mobile.database.RetrofitClient1
//import com.example.agrisynergi_mobile.database.RetrofitClient1
import com.example.agrisynergi_mobile.navigation.Screen
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.RegisterViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

@Composable
fun RegisterScreen(
    navController: NavHostController, registerViewModel: RegisterViewModel, registerWithGoogle:()->Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context: Context = LocalContext.current


    LaunchedEffect(registerViewModel.registerResult.value) {
        val result = registerViewModel.registerResult.value
        if (result == "Registration successful") {
            // Tampilkan Toast dan navigasi ke layar login atau konsultasi
            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Login.route)
        } else if (result.startsWith("Registration failed")) {
            // Tampilkan pesan kesalahan jika registrasi gagal
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF13382C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "REGISTER",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF6FB7A)
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Ayo Daftar dan bergabung dengan AgriSynergy",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Input Fields
            OutlinedTextFieldWithIcon(
                value = username,
                onValueChange = { username = it },
                label = "Username",
                icon = R.drawable.iconprofile1
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextFieldWithIcon(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                icon = R.drawable.iconemail
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextFieldWithIcon(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = "Nomor Handphone",
                icon = R.drawable.icontelp
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextFieldWithIcon(
                value = address,
                onValueChange = { address = it },
                label = "Alamat",
                icon = R.drawable.icontelp
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextFieldWithIcon(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                icon = R.drawable.iconvisibility,
                isPasswordField = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextFieldWithIcon(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                icon = R.drawable.iconvisibility,
                isPasswordField = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Validasi form terlebih dahulu
                    if (username.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || address.isEmpty()) {
                        errorMessage = "Semua kolom harus diisi!"
                    } else if (password != confirmPassword) {
                        errorMessage = "Password dan Confirm Password tidak cocok!"
                    } else {
                        isLoading = true
                        errorMessage = null
                        registerViewModel.registerUser(username, email, password, address, phoneNumber)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B8C51))
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(text = "Daftar", color = Color.White, fontSize = 18.sp)
                }
            }

            // Button Daftar
//            Button(
//                onClick = {
//                    if (password == confirmPassword) {
//                        isLoading = true
//                        errorMessage = null
//                        registerUser(
//                            username = username,
//                            email = email,
//                            phoneNumber = phoneNumber,
//                            password = password,
//                            address = address,
//                            onSuccess = {
//                                isLoading = false
//                                navController.navigate(Screen.Beranda.route)
//                            },
//                            onError = { error ->
//                                isLoading = false
//                                errorMessage = error
//                            }
//                        )
//                    } else {
//                        errorMessage = "Password tidak cocok!"
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B8C51))
//            ) {
//                if (isLoading) {
//                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
//                } else {
//                    Text(text = "Daftar", color = Color.White, fontSize = 18.sp)
//                }
//            }

            //pesan error
            if (errorMessage != null) {
                Text(text = errorMessage!!, color = Color.Red, modifier = Modifier.padding(vertical = 8.dp))
            }

            // Daftar dengan Google
            Text(
                text = "atau lanjutkan dengan",
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(
                onClick = { registerWithGoogle() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                border = BorderStroke(2.dp, Color(0xFF5B8C51)),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF13382C)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Daftar dengan Google",
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.google_1),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Teks "Have an account? Login"
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Have an account?", color = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                    Text(text = "Login", color = Color(0xFFF6FB7A))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldWithIcon(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: Int,
    isPasswordField: Boolean = false
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White, shape = RoundedCornerShape(14.dp)),
        singleLine = true,
        visualTransformation = if (isPasswordField && !passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { if (isPasswordField) passwordVisibility = !passwordVisibility }) {
                Icon(
                    painter = painterResource(
                        id = if (isPasswordField) {
                            if (passwordVisibility) R.drawable.iconvisibility else R.drawable.iconvisibility
                        } else icon
                    ),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            containerColor = Color.Transparent
        )
    )
}

fun registerUser(
    username: String,
    email: String,
    phoneNumber: String,
    password: String,
    address: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val api = RetrofitClient1.instance
    //validasi apakah field kosong
    if (username.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || address.isEmpty()) {
        onError("All fields must be filled")
        return
    }

    // log
    Log.d("Register", "Username: $username")
    Log.d("Register", "Email: $email")
    Log.d("Register", "Phone: $phoneNumber")
    Log.d("Register", "Password: $password")
    Log.d("Register", "Address: $address")

    // mengirim request data registrasi
    val call = api.register(
        name = username,
        email = email,
        phoneNumber = phoneNumber,
        address = address,
        password = password
    )

    call.enqueue(object : retrofit2.Callback<UserResponse> {
        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
            if (response.isSuccessful) {
                val userResponse = response.body()
                if (userResponse != null && userResponse.success) {
                    onSuccess()
                } else {
                    // jika tidak sukses handle error
                    val errorMessage = userResponse?.message ?: "Registration failed!"
                    userResponse?.errors?.missingFields?.let { missingFields ->
                        val missingFieldsMessage = "Missing Fields: ${missingFields.joinToString(", ")}"
                        Log.e("RegisterError", missingFieldsMessage)
                        onError("$errorMessage\n$missingFieldsMessage")
                    } ?: onError(errorMessage)
                }
            } else {
                //respon handle error
                val errorBody = response.errorBody()?.string()
                Log.e("RegisterError", "Error Body: $errorBody")
                onError("Error occurred during registration: $errorBody")
            }
        }

        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
            Log.e("RegisterError", "Network Failure: ${t.localizedMessage}")
            onError("Network error occurred: ${t.localizedMessage}")
        }
    })
}



@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
//    RegisterScreen(navController = rememberNavController(),)
}