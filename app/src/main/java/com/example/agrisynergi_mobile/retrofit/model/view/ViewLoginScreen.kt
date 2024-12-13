package com.example.agrisynergi_mobile.retrofit.model.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.navigation.Screen
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.LoginViewModel


@Composable
fun LoginScreen(navController: NavController,viewModel: LoginViewModel, context: Context,loginWithGoogle: () -> Unit){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(viewModel.loginResult.value) {
        if (viewModel.loginResult.value == "Login successful") {
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            navController.navigate(Screen.Beranda.route)
        } else if (viewModel.loginResult.value.startsWith("Login failed")) {
            Toast.makeText(context, viewModel.loginResult.value, Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        Box(modifier = Modifier.background(Color.White)){
            Column (modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center){
                Text("Welcome bro, here you must login before start your activity", fontSize = 20.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center)
                Spacer(Modifier.height(20.dp))
                val image = painterResource(R.drawable.agri_synergy_3)
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth().size(200.dp).padding(8.dp)
                )

                Text("Username", fontWeight = FontWeight.Medium)
                androidx.compose.material.OutlinedTextField(value = username, onValueChange = {
                    username = it
                    isLoading = false
                },
                    modifier = Modifier.fillMaxWidth(), singleLine = true,
                    colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(R.color.hijau_muda),
                        unfocusedBorderColor = colorResource(R.color.ijo),
                        textColor = Color.Black )
                    )
                Text("Password",fontWeight = FontWeight.Medium)
                androidx.compose.material.OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        isLoading = false
                    },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(R.color.hijau_muda),
                        unfocusedBorderColor = colorResource(R.color.ijo),
                        textColor = Color.Black )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Forgot password?", style = TextStyle(
                    color = colorResource(R.color.ic_launcher_background),
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                ),
                    modifier = Modifier.clickable {

                    }
                )
                Spacer(modifier = Modifier.height(8.dp))


                Button(onClick = {
//                     Validasi input sebelum login
                    if (username.isNotBlank() && password.isNotBlank()) {
                        isLoading = true
                        viewModel.login(username, password)

                    } else {
                        Toast.makeText(context, "Please enter username and password", Toast.LENGTH_SHORT).show()
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
                            Text(text = "Masuk", color = Color.White, fontSize = 18.sp)
                        }
                    }

                val styleYo = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Don't have an account?", color = Color.Black,
                        style = styleYo
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Register here",
                        style = TextStyle(
                            color = colorResource(R.color.ic_launcher_background),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Regist.route)
                        }
                    )
                }

                Text("Or login with",fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))


                Row(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
                    loginWithGoogle()
                }, horizontalArrangement = Arrangement.SpaceEvenly) {
                    Box(modifier = Modifier.background(Color.White, shape = RoundedCornerShape(16.dp)).padding(6.dp)){
                        Image(
                            painter = painterResource(R.drawable.google_1),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                }
            }
        }
    }
}