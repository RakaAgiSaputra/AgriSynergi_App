package com.example.agrisynergi_mobile.User

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberImagePainter

import com.example.agrisynergi_mobile.R

class StartSellingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationApp()
        }
    }
}

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "start_selling") {
        composable("start_selling") {
            StartSellingScreen(
                onRegisterClick = {
                    navController.navigate("store_info")
                },
                onBackPressed = { navController.popBackStack() }
            )
        }
        composable("store_info") {
            StoreInfoScreen(
                onBackClick = { navController.popBackStack() },
                onNextClick = { navController.navigate("tambah_produk") }
            )
        }
        composable("tambah_produk") {
            TambahProdukScreen(onBackClick = { navController.popBackStack() }) {
                navController.navigate("order_success")
            }
        }
        composable("order_success") {
            OrderSuccessScreen(onBackClick = { navController.popBackStack() })
        }
    }
}


@Composable
fun StartSellingScreen(onRegisterClick: () -> Unit, onBackPressed: () -> Unit) {
    val context = LocalContext.current as Activity
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF13382C))
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { context.startActivity(Intent(context, UserProfileActivity::class.java)) } // Navigate to UserProfileActivity
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Selamat Datang Ke AgriSynergy",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                StartSellingContent(onRegisterClick = onRegisterClick)
            }
        }
    )
}


@Composable
fun StartSellingContent(onRegisterClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_start_selling),
            contentDescription = "Start Selling Image",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selamat Datang Ke AgriSynergy",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Untuk mulai berjualan, daftar sebagai penjual dengan melengkapi informasi yang diperlukan",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onRegisterClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(48.dp)
        ) {
            Text("Mulai Pendaftaran", color = Color.White)
        }
    }
}

@Composable
fun StoreInfoScreen(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF13382C))
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Atur Informasi Toko",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.weight(1f))
                TextButton(onClick = onNextClick) {
                    Text("Simpan", color = Color.White)
                }
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                EditableFormField(label = "Nama Toko *", placeholder = "Masukkan Nama Toko")
                EditableFormField(label = "Alamat & Jasa Pengiriman *", placeholder = "Masukkan Alamat Toko")
                EditableFormField(label = "Email", placeholder = "Masukkan Email kamu")
                EditableFormField(label = "Nomor Telepon", placeholder = "Masukkan Nomor Telepon kamu")

                Spacer(Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .height(48.dp)
                    ) {
                        Text("Kembali", color = Color(0xFF13382C))
                    }
                    Button(
                        onClick = onNextClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .height(48.dp)
                    ) {
                        Text("Lanjut", color = Color.White)
                    }
                }
            }
        }
    )
}


@Composable
fun EditableFormField(label: String, placeholder: String) {
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}

@Composable
fun TambahProdukScreen(onBackClick: () -> Unit,  onUploadClick: () -> Unit) {
    val context = LocalContext.current as Activity
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }

    // Launcher untuk memilih gambar
    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImages = selectedImages + it
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF13382C))
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tambah Produk",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .border(1.dp, Color.Gray)
                            .clickable { pickImageLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+ Tambah Foto", color = Color.Gray, fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    LazyRow {
                        items(selectedImages) { uri ->
                            Image(
                                painter = rememberImagePainter(data = uri),
                                contentDescription = "Selected Image",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(horizontal = 4.dp)
                                    .clipToBounds()
                                    .border(1.dp, Color.Gray)
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                EditableFormField(label = "Nama Produk *", placeholder = "Masukan nama produk")
                EditableFormField(label = "Deskripsi Produk *", placeholder = "Masukan Deskripsi produk")
                EditableFormField(label = "Kategori *", placeholder = "Masukkan Kategori")
                EditableFormField(label = "Harga*", placeholder = "Masukkan Harga")

                Spacer(Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .height(48.dp)
                    ) {
                        Text("Kembali", color = Color(0xFF13382C))
                    }
                    Button(
                        onClick = onUploadClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .height(48.dp)
                    ) {
                        Text("Upload Produk", color = Color.White)
                    }
                }
            }
        }
    )
}

@Composable
fun OrderSuccessScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF13382C))
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tambah Produk",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_thumbs_up),
                    contentDescription = "Order Success Image",
                    modifier = Modifier.size(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Berhasil menambahkan produk!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Anda telah berhasil menambahkan produk ke toko Anda.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(48.dp)
                ) {
                    Text("Beranda", color = Color.White)
                }
            }
        }
    )
}


