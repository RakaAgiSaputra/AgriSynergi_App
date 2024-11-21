package com.example.agrisynergi_mobile.User

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

import com.example.agrisynergi_mobile.R
class TambahPostinganActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TambahPostinganScreen(
                onBackClicked = {
                    finish()
                }
            )
        }
    }
}

@Composable
fun HeaderSection(title: String, onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .padding(start = 16.dp)
                .clickable { onBackClicked() }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun TambahPostinganScreen(onBackClicked: () -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))

    ) {
        // Header
        HeaderSection(title = "Tambah Postingan", onBackClicked = onBackClicked)

        Spacer(modifier = Modifier.height(20.dp))

        // Image Picker Section with Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp)
                .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5F897B))
                ) {
                    Text("Pilih Gambar", color = Color(0xFFF0F0F0))
                }
            } else {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Button over the image to allow re-selection
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.align(Alignment.Center).padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5F897B)
                    )
                ) {
                    Text("Ganti Gambar", color = Color(0xFFF0F0F0))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Caption Text Field with auto-sizing height
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Tambah Caption") },
            placeholder = { Text("Tulis disini..") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            maxLines = Int.MAX_VALUE
        )

        Spacer(modifier = Modifier.weight(1f)) // Pushes the "Posting" button to the bottom

        // Posting Button at the Bottom
        Button(
            onClick = { /* Handle posting action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5F897B)
            )
        ) {
            Text("Posting",  color = Color(0xFFF0F0F0))
        }
    }
}
