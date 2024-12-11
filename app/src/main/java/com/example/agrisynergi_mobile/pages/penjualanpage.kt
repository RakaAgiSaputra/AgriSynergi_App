package com.example.agrisynergi_mobile.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R

@Composable
fun PenjualanScreen( navController: NavHostController) {
    Column{
        PenjualanTopBar(
            onBackClick = { navController.navigateUp() }
        )
        Header()
        Spacer(modifier = Modifier.height(8.dp))
        PesananCard(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenjualanTopBar(
    onBackClick: () -> Unit
) {
    androidx.compose.material.TopAppBar(
        backgroundColor = Color(0xFF13382C)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onBackClick() }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.iconback),
                    contentDescription = "Icon Back",
                    tint = Color.White
                )
            }
            Text(
                text = "Penjualan Saya",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            IconButton(
                onClick = { /*TODO*/ }
            ) {
            }
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Belum Bayar",
            modifier = Modifier.padding(8.dp),
            color = Color.Gray
        )
        Text(
            text = "Perlu dikirim",
            modifier = Modifier.padding(8.dp),
            color = Color(0xFF008504),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Dikirim",
            modifier = Modifier.padding(8.dp),
            color = Color.Gray
        )
        Text(
            text = "Selesai",
            modifier = Modifier.padding(8.dp),
            color = Color.Gray
        )
    }
}

@Composable
fun PesananCard(navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_market2),
                    contentDescription = "Toko",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "jayatani_shop",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_beras_jagung),
                    contentDescription = "Gambar Produk",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Beras Jagung",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Varian: 1kg",
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Rp.150.000",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

                Text(
                    text = "1",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "1 Produk",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = "Total Pesanan Rp167.000",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Mohon kirim/atur pengiriman sebelum 16 Des 2024 13:30 untuk menghindari keterlambatan pengiriman",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    ),
                    color = Color.Gray,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { navController.navigate("pengiriman") },
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF216851),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Atur pengiriman")
                }
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "No. Pesanan",
                    modifier = Modifier
                        .padding(8.dp),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "#221213HU4YAJD6",
                    modifier = Modifier
                        .padding(8.dp),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
