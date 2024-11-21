package com.example.agrisynergi_mobile.User

import com.example.agrisynergi_mobile.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DropshipperCatalogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropshipperCatalogScreen(
                onBackPressed = { finish() },
                onAddClicked = {
                    // Implement add functionality or navigation to add item screen here
                }
            )
        }
    }
}

@Composable
fun DropshipperCatalogScreen(onBackPressed: () -> Unit, onAddClicked: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(72.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(18.dp),
                        ambientColor = Color.Black.copy(alpha = 1f),
                        spotColor = Color.Black.copy(alpha = 1f)
                    )
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFF5F897B))
                    .clickable { onAddClicked() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Add New Item",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }
        },
        content = { padding ->
            Column(modifier = Modifier.fillMaxSize().padding(padding)) {
                DropshipperCatalogHeader(onBackPressed = onBackPressed)

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(6.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        CatalogItem(
                            imageResource = R.drawable.farm_set_image,
                            title = "Farm-set",
                            terjual = "123",
                            price = "Rp. 120.000"
                        )
                    }
                    item {
                        CatalogItem(
                            imageResource = R.drawable.img_jagung_original,
                            title = "Jagung Original per Kg",
                            terjual = "123",
                            price = "Rp. 7.000"
                        )
                    }
                    item {
                        CatalogItem(
                            imageResource = R.drawable.img_beras_jagung,
                            title = "Farm-set",
                            terjual = "123",
                            price = "Rp. 13.000"
                        )
                    }
                    item {
                        CatalogItem(
                            imageResource = R.drawable.img_serbuk_jagung,
                            title = "Fertilizer Spreader",
                            terjual = "123",
                            price = "Rp. 5.000"
                        )
                    }
                    item {
                        CatalogItem(
                            imageResource = R.drawable.farm_set_image,
                            title = "Farm-set",
                            terjual = "123",
                            price = "Rp. 120.000"
                        )
                    }
                    item {
                        CatalogItem(
                            imageResource = R.drawable.img_jagung_original,
                            title = "Jagung Original per Kg",
                            terjual = "123",
                            price = "Rp. 7.000"
                        )
                    }
                    item {
                        CatalogItem(
                            imageResource = R.drawable.img_beras_jagung,
                            title = "Farm-set",
                            terjual = "123",
                            price = "Rp. 13.000"
                        )
                    }
                    item {
                        CatalogItem(
                            imageResource = R.drawable.img_serbuk_jagung,
                            title = "Fertilizer Spreader",
                            terjual = "123",
                            price = "Rp. 5.000"
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun DropshipperCatalogHeader(onBackPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(horizontal = 16.dp, vertical = 18.dp),
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
            text = "Katalog Dropshipper",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CatalogItem(imageResource: Int, title: String, terjual: String, price: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(3.dp, RoundedCornerShape(5.dp), clip = false)
            .background(Color(0xFFE0E0E0).copy(alpha = 0.5f), RoundedCornerShape(5.dp)) // Transparent shadow color
            .clip(RoundedCornerShape(5.dp))
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(5.dp))
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = title,
                contentScale = ContentScale.Crop, // Crops the image to fit the container
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Keeps a square aspect ratio
                    .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Text(
                text = "$terjual terjual",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = price,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .padding(bottom = 8.dp)
            )
        }
    }
}


