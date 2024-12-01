package com.example.agrisynergi_mobile.pages.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.navigation.Screen

@Composable
fun RegisterScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF13382C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "REGISTER",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF6FB7A) // Warna kuning
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
                value = "",
                onValueChange = {},
                label = "Username",
                icon = R.drawable.iconprofile1
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextFieldWithIcon(
                value = "",
                onValueChange = {},
                label = "Email",
                icon = R.drawable.iconemail
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextFieldWithIcon(
                value = "",
                onValueChange = {},
                label = "Nomor Handphone",
                icon = R.drawable.icontelp
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextFieldWithIcon(
                value = "",
                onValueChange = {},
                label = "Password",
                icon = R.drawable.iconvisibility,
                isPasswordField = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextFieldWithIcon(
                value = "",
                onValueChange = {},
                label = "Confirm Password",
                icon = R.drawable.iconvisibility,
                isPasswordField = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Button Daftar
            Button(
                onClick = { navController.navigate(Screen.Beranda.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B8C51))
            ) {
                Text(text = "Daftar", color = Color.White, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "atau lanjutkan dengan",
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(
                onClick = { /* Aksi Login Google */ },
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

    Box(
        modifier = Modifier
            .width(360.dp)
            .height(50.dp)
            .background(Color.White, shape = RoundedCornerShape(14.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                modifier = Modifier
                    .weight(1f),
                singleLine = true,
                visualTransformation = if (isPasswordField && !passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    containerColor = Color.Transparent
                )
            )

            IconButton(
                onClick = {
                    if (isPasswordField) passwordVisibility = !passwordVisibility
                },
                enabled = isPasswordField || !isPasswordField
            ) {
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}
