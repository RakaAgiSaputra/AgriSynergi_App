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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.agrisynergi_mobile.R
class OrderHistoryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderHistoryScreen { finish() }
        }
    }
}

@Composable
fun OrderHistoryScreen(onBackPressed: () -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Belum Bayar", "Dikemas", "Dikirim", "Selesai")

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        OrderHistoryHeader(onBackPressed)

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTabIndex == index) Color(0xFFFF9D00) else Color.Gray
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> NoOrderContent("Belum Bayar")
            1 -> NoOrderContent("Dikemas")
            2 -> NoOrderContent("Dikirim")
            3 -> CompletedOrdersContent()
        }
    }
}

@Composable
fun OrderHistoryHeader(onBackPressed: () -> Unit) {
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
            text = "Riwayat Pesanan",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun NoOrderContent(tabName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_shopping_cart), // ganti dengan ikon keranjang kosong yang sesuai
            contentDescription = "Empty Cart",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Kamu belum punya pesanan nih", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Text("Ayo berburu produk di AgroSynergy!", color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Navigate to shopping page */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Pesan Sekarang", color = Color.White)
        }
    }
}

@Composable
fun CompletedOrdersContent() {
    val completedOrders = listOf(
        OrderItem(
            imageRes = R.drawable.img_jagung_original,
            name = "Jagung Original",
            price = "Rp. 20.900",
            quantity = 2,
            totalPrice = "Rp. 41.800"
        ),
        OrderItem(
            imageRes = R.drawable.img_beras_jagung,
            name = "Beras Jagung",
            price = "Rp. 23.450",
            quantity = 1,
            totalPrice = "Rp. 23.450"
        ),
        OrderItem(
            imageRes = R.drawable.img_serbuk_jagung,
            name = "Serbuk Jagung",
            price = "Rp. 10.220",
            quantity = 1,
            totalPrice = "Rp. 10.220"
        )
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        completedOrders.forEachIndexed { index, order ->
            CompletedOrderItem(order)
            if (index < completedOrders.size - 1) {
                Divider(
                    color = Color(0xFFF0F2F5),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun CompletedOrderItem(order: OrderItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = order.imageRes),
            contentDescription = order.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = order.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "${order.quantity} Produk", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Total : ${order.totalPrice}", fontSize = 14.sp, color = Color.Gray)
        }

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0F7EF)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "Selesai", color = Color(0xFF008504))
        }
    }
}

data class OrderItem(
    val imageRes: Int,
    val name: String,
    val price: String,
    val quantity: Int,
    val totalPrice: String
)

