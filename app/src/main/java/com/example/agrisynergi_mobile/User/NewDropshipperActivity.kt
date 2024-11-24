package com.example.agrisynergi_mobile.User

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrisynergi_mobile.R

class NewDropshipperActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewDropshipperScreen{finish()}
        }
    }
}

@Composable
fun NewDropshipperScreen(onBackPressed: () -> Unit) {
    var currentScreen by remember { mutableStateOf("main") }

    when (currentScreen) {
        "main" -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp)
            ) {
                NewDropshipperHeader(onBackPressed)
                Spacer(modifier = Modifier.height(16.dp))
                TitleSection()
                Spacer(modifier = Modifier.height(16.dp))
                InfoSection()
                Spacer(modifier = Modifier.height(16.dp))
                TutorImage()
            }
            ExploreButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                onClick = { currentScreen = "tutor2" }
            )
        }
        "tutor2" -> Tutor2(
            onBackClick = { currentScreen = "main" },
            onNextClick = { currentScreen = "tutor3" }
        )
        "tutor3" -> Tutor3(
            onBackClick = { currentScreen = "tutor2" },
            onNextClick = { currentScreen = "tutor4" }
        )
        "tutor4" -> Tutor4(
            onBackClick = { currentScreen = "tutor3" },
            onNextClick = { currentScreen = "tutor5" }
        )
        "tutor5" -> Tutor5(
            onBackClick = { currentScreen = "tutor4" },
            onNextClick = { currentScreen = "tutor5" }
        )
    }
}

@Composable
fun NewDropshipperHeader(onBackPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
                .clickable { onBackPressed() }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Katalog Saya",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun InfoSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFF5F897B), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "1. Dapatkan keuntungan hingga jutaan rupiah per bulan\n" +
                    "2. Tidak ada biaya awal\n" +
                    "3. Tidak ada manajemen inventaris",
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun TitleSection() {
    Text(
        text = "Mulai bisnis online dan jadilah penjual dropshipper di AGRI SYNERGY!",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF13382C),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}

@Composable
fun TutorImage() {
    Image(
        painter = painterResource(id = R.drawable.img_tutor_1ds),
        contentDescription = "Tutor Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(550.dp)
    )
}

@Composable
fun ExploreButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B))
    ) {
        Text(
            text = "Mulai Eksplor Katalog Saya",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun Tutor2(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        CatalogHeader(onBackClick)
        Spacer(modifier = Modifier.height(16.dp))
        EmptyCatalogSection()
        Spacer(modifier = Modifier.height(16.dp))
        Tutor2Image(onNextClick)
    }
}

@Composable
fun CatalogHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .clickable { onBackClick() }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Katalog Saya",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun EmptyCatalogSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_market2),
            contentDescription = "Empty Catalog",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Belum ada produk. Yuk, mulai tambahin produk sekarang!",
            fontSize = 14.sp,
            color = Color(0xFF13382C),
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B))
        ) {
            Text(text = "Tambah Produk", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Pelajari bagaimana memulai katalog saya",
            fontSize = 14.sp,
            color = Color(0xFF0B0B0B),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            painter = painterResource(id = R.drawable.tutor1_berjalan),
            contentDescription = "tutor berjalan",
            modifier = Modifier
                .width(250.dp)
        )
    }
}

@Composable
fun Tutor2Image(onNextClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_tutor2_ds),
            contentDescription = "Tutor2 Image",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .height(550.dp)
        )
        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
            modifier = Modifier
                .width(180.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            Text(text = "Next", color = Color.White)
        }
    }
}

@Composable
fun Tutor3(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        CatalogHeader(onBackClick)
        Spacer(modifier = Modifier.height(16.dp))
        Empty2CatalogSection()
        Spacer(modifier = Modifier.height(16.dp))
        Tutor3Image(onNextClick)
    }
}

@Composable
fun Empty2CatalogSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_market2),
            contentDescription = "Empty Catalog",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Belum ada produk. Yuk, mulai tambahin produk sekarang!",
            fontSize = 14.sp,
            color = Color(0xFF13382C),
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B))
        ) {
            Text(text = "Tambah Produk", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Pelajari bagaimana memulai katalog saya",
            fontSize = 14.sp,
            color = Color(0xFF0B0B0B),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            painter = painterResource(id = R.drawable.tutor2_berjalan),
            contentDescription = "tutor berjalan",
            modifier = Modifier
                .width(250.dp)
        )
    }
}

@Composable
fun Tutor3Image(onNextClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_tutor3_ds),
            contentDescription = "Tutor3 Image",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .height(550.dp)
        )
        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
            modifier = Modifier
                .width(180.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            Text(text = "Next", color = Color.White)
        }
    }
}

@Composable
fun Tutor4(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        CatalogHeader(onBackClick)
        Spacer(modifier = Modifier.height(16.dp))
        Empty3CatalogSection()
        Spacer(modifier = Modifier.height(16.dp))
        Tutor4Image(onNextClick)
    }
}

@Composable
fun Empty3CatalogSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_market2),
            contentDescription = "Empty Catalog",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Belum ada produk. Yuk, mulai tambahin produk sekarang!",
            fontSize = 14.sp,
            color = Color(0xFF13382C),
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B))
        ) {
            Text(text = "Tambah Produk", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Pelajari bagaimana memulai katalog saya",
            fontSize = 14.sp,
            color = Color(0xFF0B0B0B),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            painter = painterResource(id = R.drawable.tutor3_berjalan),
            contentDescription = "tutor berjalan",
            modifier = Modifier
                .width(250.dp)
        )
    }
}

@Composable
fun Tutor4Image(onNextClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_tutor4_ds),
            contentDescription = "Tutor4 Image",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .height(550.dp)
        )
        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
            modifier = Modifier
                .width(180.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            Text(text = "Next", color = Color.White)
        }
    }
}

@Composable
fun Tutor5(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        CatalogHeader(onBackClick)
        Spacer(modifier = Modifier.height(16.dp))
        Empty4CatalogSection()
        Spacer(modifier = Modifier.height(16.dp))
        Tutor5Image(onNextClick)
    }
}

@Composable
fun Empty4CatalogSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_market2),
            contentDescription = "Empty Catalog",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Belum ada produk. Yuk, mulai tambahin produk sekarang!",
            fontSize = 14.sp,
            color = Color(0xFF13382C),
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B))
        ) {
            Text(text = "Tambah Produk", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Pelajari bagaimana memulai katalog saya",
            fontSize = 14.sp,
            color = Color(0xFF0B0B0B),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            painter = painterResource(id = R.drawable.tutor4_berjalan),
            contentDescription = "tutor berjalan",
            modifier = Modifier
                .width(250.dp)
        )
    }
}

@Composable
fun Tutor5Image(onNextClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_tutor5_ds),
            contentDescription = "Tutor5 Image",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .height(550.dp)
        )
        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
            modifier = Modifier
                .width(180.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            Text(text = "Tambah Produk", color = Color.White)
        }
    }
}
