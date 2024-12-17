//package com.example.agrisynergi_mobile.pages
//
//import android.content.Context
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.calculateEndPadding
//import androidx.compose.foundation.layout.calculateStartPadding
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.windowInsetsPadding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.NavigationBarItemDefaults
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.RectangleShape
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.navigation.NavHostController
//import com.example.agrisynergi_mobile.R
//import com.example.agrisynergi_mobile.navigation.Screen
//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.HorizontalPager
//import com.google.accompanist.pager.rememberPagerState
//
//@Composable
//fun TokoScreen(navController: NavHostController) {
//    Column {
//        TokoSayaTopBar(
//            onBackClick = { navController.navigateUp() }
//        )
//        TokoSayaScreen(navController)
//        PesananStatusRow()
//        PengumumanSection()
//        SaldoCard()
//    }
//}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TokoSayaTopBar(
//    onBackClick: () -> Unit
//) {
//    androidx.compose.material.TopAppBar(
//        backgroundColor = Color(0xFF13382C)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            IconButton(
//                onClick = { onBackClick() }
//            ) {
//                Icon(
//                    modifier = Modifier.size(30.dp),
//                    painter = painterResource(id = R.drawable.iconback),
//                    contentDescription = "Icon Back",
//                    tint = Color.White
//                )
//            }
//            Text(
//                text = "Toko Saya",
//                style = TextStyle(
//                    fontSize = 20.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold
//                )
//            )
//            IconButton(
//                onClick = { /*TODO*/ }
//            ) {
//            }
//        }
//    }
//}
//
//@Composable
//fun TokoSayaScreen(navController: NavHostController) {
//    Spacer(modifier = Modifier.height(8.dp))
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        shape = RectangleShape,
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Spacer(modifier = Modifier.height(8.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Spacer(modifier = Modifier.width(8.dp))
//            // Profil toko
//            Image(
//                painter = painterResource(id = R.drawable.ic_profile), // Gambar profil toko
//                contentDescription = "Foto Toko",
//                modifier = Modifier
//                    .size(50.dp)
//                    .clip(RoundedCornerShape(25.dp))
//                    .background(Color.Gray)
//            )
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//            ) {
//                Text(text = "Jayatani.shop", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                Text(text = "agrisy.co.id/121312312rqd", fontSize = 14.sp, color = Color.Gray)
//            }
//
//            OutlinedButton(
//                onClick = { navController.navigate("detailtoko") },
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF))
//            ) {
//                Text("Kunjungi toko", color = Color(0xFF5B8C51))
//            }
//            Spacer(modifier = Modifier.width(24.dp))
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//    }
//}
//
//@Composable
//fun PesananStatusRow() {
//    Spacer(modifier = Modifier.height(8.dp))
//    Card(
//        modifier = Modifier
//            .fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        shape = androidx.compose.ui.graphics.RectangleShape,
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Text(
//            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
//            text = "Status Pesanan",
//            style = TextStyle(
//                fontSize = 16.sp,
//                color = Color.Black,
//                fontWeight = FontWeight.Bold
//            )
//        )
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            PesananStatusItem("1", "Perlu Dikirim")
//            PesananStatusItem("0", "Pembatasan")
//            PesananStatusItem("0", "Pengembalian")
//            PesananStatusItem("0", "Penilaian")
//        }
//    }
//}
//
//@Composable
//fun PesananStatusItem(count: String, label: String) {
//    Spacer(modifier = Modifier.height(8.dp))
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = count, fontSize = 20.sp, fontWeight = FontWeight.Bold)
//        Text(text = label, fontSize = 12.sp, color = Color.Black)
//    }
//}
//
//@Composable
//fun PengumumanSection() {
//    Spacer(modifier = Modifier.height(8.dp))
//    Card(
//        modifier = Modifier
//            .fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        shape = androidx.compose.ui.graphics.RectangleShape,
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Text(
//            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
//            text = "Pengumuman Penjual",
//            style = TextStyle(
//                fontSize = 16.sp,
//                color = Color.Black,
//                fontWeight = FontWeight.Bold
//            )
//        )
//        Image(
//            painter = painterResource(id = R.drawable.start_selling), // Ganti dengan gambar promo
//            contentDescription = "Pengumuman",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .height(150.dp)
//                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
//        )
//    }
//}
//
//@Composable
//fun SaldoCard() {
//    Spacer(modifier = Modifier.height(8.dp))
//    Card(
//        modifier = Modifier
//            .fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        shape = androidx.compose.ui.graphics.RectangleShape,
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column {
//                Text(text = "Total Saldo Aktif", fontSize = 14.sp, color = Color.Gray)
//                Text(text = "Rp 150.000", fontSize = 20.sp, fontWeight = FontWeight.Bold)
//            }
//
//            Button(
//                onClick = { /* Aksi tarik saldo */ },
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF13382C)),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text("Tarik Saldo", color = Color.White)
//            }
//        }
//    }
//}