package com.example.agrisynergi_mobile.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.User.TokoSayaTopBar
import com.example.agrisynergi_mobile.data.Market
import com.example.agrisynergi_mobile.data.datamarket
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun DetailTokoScreen(navController: NavHostController) {
    Column {
        TokoSayaTopBar(
            onBackClick = { navController.navigateUp() }
        )
        DetailTokoSayaScreen(navController)
        TabToko(navController)
    }
}

@Composable
fun DetailTokoSayaScreen(navController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Gray),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Foto Toko",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Jayatani.shop", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Status Online",
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Online",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "10 Pengikut",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = { navController.navigate("detailtoko") },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.hijau_tua))
            ) {
                Text("Ubah", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(24.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            shape = RectangleShape,
            onClick = { navController.navigate("detailtoko") },
            colors = ButtonDefaults.buttonColors( containerColor = Color.White.copy(alpha = 0.3f) ),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Icon Plus",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Tambah produk",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun TabToko(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Toko", "Produk", "Kategori")

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
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
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.SemiBold,
                            color = if (selectedTabIndex == index) Color(0xFF5B8C51) else Color.Black
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> HighlightToko(navController, datamarket.markets)
            1 -> ProdukGrid(navController, datamarket.markets)
            2 -> KategoriContent()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HighlightToko(
    navController: NavHostController,
    items: List<Market>,
    modifier: Modifier = Modifier
) {
    val HighlightList = listOf(
        R.drawable.highlight1,
        R.drawable.highlight2,
        R.drawable.highlight3
    )
    val pagerState = rememberPagerState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            HorizontalPager(
                count = HighlightList.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = HighlightList[page]),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(18.dp),
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Produk Unggulan",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(items) { market ->
                            ProdukItem(navController, market)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
fun ProdukItem(navController: NavHostController, market: Market) {
    val itemWidth = 160.dp
    val itemHeight = 230.dp

    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(itemWidth)
            .height(itemHeight)
            .clickable {
                navController.navigate("detailmarket/${market.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = market.imageUrl),
                contentDescription = "Image Market",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = market.title,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = market.description,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = market.price,
                style = TextStyle(
                    fontSize = 13.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProdukGrid(navController: NavHostController, items: List<Market>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(items) { market ->
            ProdukItem(navController, market)
        }
    }
}

@Composable
fun NoProductContent(tabName: String) {
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
        Text("Kamu belum punya produk nih", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Text("Ayo jual produkmu di AgriSynergy!", color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Navigasi ke page tambah produk */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Tambah Produk", color = Color.White)
        }
    }
}

@Composable
fun KategoriContent() {
    val completedOrders = listOf(
        KategoriItem(
            imageRes = R.drawable.img_traktor_hdd,
            name = "Peralatan Bertani",
            price = "Rp. 20.900",
            quantity = 1,
            totalPrice = "Rp. 41.800"
        ),
        KategoriItem(
            imageRes = R.drawable.img_beras_jagung,
            name = "Bibit",
            price = "Rp. 23.450",
            quantity = 4,
            totalPrice = "Rp. 23.450"
        ),
        KategoriItem(
            imageRes = R.drawable.img_serbuk_jagung,
            name = "Pupuk",
            price = "Rp. 10.220",
            quantity = 1,
            totalPrice = "Rp. 10.220"
        )
    )

    Column {
        completedOrders.forEachIndexed { index, kategori ->
            KategoriItem(kategori, modifier = Modifier.fillMaxSize().padding(16.dp))
            if (index < completedOrders.size - 1) {
                Divider(
                    color = Color(0xFFF0F2F5),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun KategoriItem(kategori: KategoriItem, modifier: Modifier) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = kategori.imageRes),
            contentDescription = kategori.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Row(modifier = Modifier.weight(1f)) {
            Text(text = kategori.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "(${kategori.quantity})",fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color.Gray)
        }

        IconButton(
            onClick = {}
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .graphicsLayer(rotationZ = 180f),
                painter = painterResource(id = R.drawable.iconback),
                contentDescription = "Icon Back",
                tint = Color.Gray
            )
        }
    }
}

data class KategoriItem(
    val imageRes: Int,
    val name: String,
    val price: String,
    val quantity: Int,
    val totalPrice: String
)