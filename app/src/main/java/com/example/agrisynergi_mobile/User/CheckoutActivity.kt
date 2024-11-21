package com.example.agrisynergi_mobile.User
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrisynergi_mobile.R

class CheckoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckoutScreen(onBackPressed = { finish() })
        }
    }
}

@Composable
fun CheckoutScreen(onBackPressed: () -> Unit) {
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                CheckoutHeader(onBackPressed = onBackPressed)
                CheckoutContent()
            }
        }
    )
}

@Composable
fun CheckoutHeader(onBackPressed: () -> Unit) {
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
            text = "Checkout",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CheckoutContent() {
    var isProductExpanded by remember { mutableStateOf(false) }
    var isSummaryExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Address Section with Border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF5F897B), RectangleShape)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_map_pin),
                    contentDescription = "Address Icon",
                    tint = Color(0xFF5F897B),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Alamat Pengiriman", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "Perumahan Setu Indah (+62823456789)\nPerumahan Setu Indah, Jalan Kenangan Setu Indah, No.14c RT 001/02, Bogor, Jakarta Selatan DKI Jakarta, ID : 12450",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF5F897B), RectangleShape)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_shop_bag),
                    contentDescription = "Product Icon",
                    tint = Color(0xFF5F897B),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Jagung Manis", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("Rp. 35.000", fontSize = 14.sp, color = Color.Gray)
                }
                Icon(
                    painter = painterResource(
                        if (isProductExpanded) R.drawable.ic_arrow_up else R.drawable.ic_back
                    ),
                    contentDescription = "Expand Product",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isProductExpanded = !isProductExpanded }
                )
            }
        }

        if (isProductExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, RectangleShape)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text("Biji Jagung - Rp. 15.000", fontSize = 14.sp, color = Color.Gray)
                Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)
                Text("Jagung Kering - Rp. 9.000", fontSize = 14.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Items Summary Section with Dropdown
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5F897B))
                .clickable { isSummaryExpanded = !isSummaryExpanded }
                .padding(12.dp)
        ) {
            Text(
                text = "Items Summary",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Icon(
                painter = painterResource(
                    if (isSummaryExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ),
                contentDescription = "Expand/Collapse",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        if (isSummaryExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal untuk Produk:", fontSize = 14.sp, color = Color.Gray)
                    Text("Rp. 35,000", fontSize = 14.sp, color = Color.Gray)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal Pengiriman:", fontSize = 14.sp, color = Color.Gray)
                    Text("Rp. 10,000", fontSize = 14.sp, color = Color.Gray)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Biaya Layanan:", fontSize = 14.sp, color = Color.Gray)
                    Text("Rp. 1,370", fontSize = 14.sp, color = Color.Gray)
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total:", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("Rp. 40,000", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Place Order Button
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(48.dp)
        ) {
            Text("Buat Pesanan", color = Color.White)
        }
    }
}
