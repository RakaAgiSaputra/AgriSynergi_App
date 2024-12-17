package com.example.agrisynergi_mobile.User
import android.os.Bundle
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

import com.example.agrisynergi_mobile.R

class AlamatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlamatScreen{ finish() }
        }
    }
}

@Composable
fun AlamatScreen(onBackPressed: () -> Unit) {
    var alamat by remember { mutableStateOf("Jl. Sudirman") }
    var kota by remember { mutableStateOf("Jakarta Selatan") }
    var provinsi by remember { mutableStateOf("Jakarta") }
    var kodepos by remember { mutableStateOf("12345") }
    var backgroundColor by remember { mutableStateOf(Color.White) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        AlamatHeader(onBackPressed)

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text("Alamat", color = Color.Black)
            FocusedOutlinedTextField(
                value = alamat,
                onValueChange = { alamat = it },
                placeholder = { Text("Masukkan Jalan", color = Color.Gray) },
                textColor = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Kota", color = Color.Black)
            FocusedOutlinedTextField(
                value = kota,
                onValueChange = { kota = it },
                placeholder = { Text("terdiri dari huruf dan angka", color = Color.Gray) },
                textColor = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Provinsi", color = Color.Black)
            FocusedOutlinedTextField(
                value = provinsi,
                onValueChange = { provinsi = it },
                placeholder = { Text("Masukkan Provinsi", color = Color.Gray) },
                textColor = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Kodepos", color = Color.Black)
            FocusedOutlinedTextField(
                value = kodepos,
                onValueChange = { kodepos = it },
                placeholder = { Text("Masukkan Kodepos", color = Color.Gray) },
                textColor = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(48.dp)
            ) {
                Text("Simpan", color = Color.White)
            }
        }
    }
}


@Composable
fun AlamatHeader(onBackPressed: () -> Unit) {
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
            text = "Alamat Pengiriman",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.weight(1f))

    }
}

//@Composable
//fun FocusedOutlinedTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    visualTransformation: VisualTransformation = VisualTransformation.None,
//    placeholder: @Composable (() -> Unit)? = null,
//    textColor: Color = Color.Black
//) {
//    var isFocused by remember { mutableStateOf(false) }
//
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        modifier = Modifier
//            .fillMaxWidth()
//            .onFocusChanged { focusState -> isFocused = focusState.isFocused },
//        visualTransformation = visualTransformation,
//        placeholder = if (value.isEmpty()) placeholder else null,
//        colors = TextFieldDefaults.colors(
//            focusedContainerColor = if (isFocused) Color(0xFFBCD7BC) else Color.Transparent,
//            focusedTextColor = textColor,
//            unfocusedTextColor = textColor,
//            focusedIndicatorColor = Color(0xFF00A305),
//            unfocusedIndicatorColor = Color(0xFF5F897B),
//        )
//    )
//}

