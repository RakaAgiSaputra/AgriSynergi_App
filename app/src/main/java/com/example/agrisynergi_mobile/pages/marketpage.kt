package com.example.agrisynergi_mobile.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.Market
import com.example.agrisynergi_mobile.data.datamarket

@Composable
fun MarketScreen(navController: NavHostController) {
    Column {
        MarketTopBar(
            onBackClick = { navController.navigateUp() }
        )
        MarketSearch()
        MarketGrid(navController, datamarket.markets)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
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
                text = "Market",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.iconkeranjang),
                    contentDescription = "Icon Keranjang",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun MarketSearch() {
    val searchQuery = remember { mutableStateOf("") }
    OutlinedTextField(
        value = searchQuery.value,
        onValueChange = { searchQuery.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .padding(horizontal = 18.dp, vertical = 4.dp),
        placeholder = {
            Text(
                text = "Search market items...",
                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
            )
        },
        label = {
            Text(
                text = "Search",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                modifier = Modifier.size(20.dp),
                tint = Color.Gray
            )
        },
        trailingIcon = {
            IconButton(onClick = { /* Navigate to filterPage */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.iconfilter),
                    contentDescription = "Filter Maps",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
            }
        }
    )
}

@Composable
fun MarketItem(navController: NavHostController, market: Market) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(230.dp)
            .clickable {
                navController.navigate("detailmarket/${market.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
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
fun MarketGrid(navController: NavHostController, items: List<Market>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(items) { market ->
            MarketItem(navController, market)
        }
    }
}

