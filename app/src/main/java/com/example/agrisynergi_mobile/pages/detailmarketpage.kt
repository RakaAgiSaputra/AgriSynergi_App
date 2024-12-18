package com.example.agrisynergi_mobile.pages

import android.media.Rating
import android.util.Log
import android.widget.RatingBar
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.datamarket
import com.example.agrisynergi_mobile.database.testDatabase.Api
import com.example.agrisynergi_mobile.database.testDatabase.Produk
import com.example.agrisynergi_mobile.database.testDatabase.RetrofitClient1
import com.example.agrisynergi_mobile.navigation.Screen
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.MarketViewModel
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.MarketViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailMarketScreen(
    marketId: Int,
    navController: NavHostController,
    api: Api = RetrofitClient1().instance
) {
    val viewModel: MarketViewModel = viewModel(factory = MarketViewModelFactory(api))
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Trigger fetch when screen is first loaded
    LaunchedEffect(Unit) {
        viewModel.fetchProducts(api)
    }

    var showBottomSheetBuyNow by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DetailTopBar(
            onBackClick = { navController.navigateUp() }
        )
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error ?: "Terjadi kesalahan",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            products.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada produk tersedia",
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            else -> {
                ContentMarket(
                    marketId = marketId,
                    modifier = Modifier.weight(1f),
                    products
                )
            }
        }
        DetailBottomBar(
            marketId = marketId,
            navController = navController,
            showBottomSheet = showBottomSheetBuyNow,
            onShowBottomSheetChange = { showBottomSheetBuyNow = it },
            modifier = Modifier.fillMaxWidth(),
            produk = products
        )
    }
    bottomSheetBuyNow(
        showBottomSheetBuyNow = showBottomSheetBuyNow,
        onShowBottomSheetChange = { showBottomSheetBuyNow = it }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
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
                text = "Detail",
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
fun ContentMarket(marketId: Int, modifier: Modifier = Modifier, produk: List<Produk>) {
    val market = produk.find { it.id_produk == marketId }
    if (market != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = "https://gtk62vzp-3000.asse.devtunnels.ms/api/fileProduk/${market.foto_produk}",
                    error = painterResource(id = R.drawable.imagenotavail)
                ),
                contentDescription = "Foto Produk",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
                    .shadow(4.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = market.nama,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            RatingBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentWidth(Alignment.Start),
                rating = market.rata_rating?.toDoubleOrNull() ?: 0.0,
                starsColor = Color(color = 0xFFFFA600),
                onRatingChanged = {},

            )
            Text(
                text = market.rata_rating ?: "Belum ada rating",
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentWidth(Alignment.Start),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rp${market.harga}",
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Deskripsi Produk:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = market.deskripsi,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentWidth(Alignment.Start)
            )
        }
    } else {
        Text(
            text = "Product not found.",
            fontSize = 16.sp,
            color = Color.Red,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double,
    starsColor: Color,
    onRatingChanged: (Double) -> Unit
) {
    var currentRating by remember { mutableStateOf(rating) }

    Row(modifier = modifier) {
        var isHalfStar = (currentRating % 1) >= 0.5
        for (index in 1..5) {
            Icon(
                modifier = Modifier
                    .clickable {
                        currentRating = index.toDouble()
                        onRatingChanged(currentRating)
                    }
                    .size(24.dp),
                imageVector = when {
                    index <= currentRating -> Icons.Rounded.Star
                    isHalfStar && index == Math.ceil(currentRating).toInt() -> {
                        isHalfStar = false
                        Icons.Rounded.StarHalf
                    }
                    else -> Icons.Rounded.StarOutline
                },
                contentDescription = "Star Rating",
                tint = starsColor
            )
        }
    }
}



@Composable
fun DetailBottomBar(
    navController: NavHostController,
    showBottomSheet: Boolean,
    marketId: Int,
    produk: List<Produk>,
    onShowBottomSheetChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    api: Api = RetrofitClient1().instance
) {
    val market = produk.find { it.id_produk == marketId }
    val viewModel: MarketViewModel = viewModel(factory = MarketViewModelFactory(api))
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            /*TODO: belum berhasil POST data ke API, bisa navigasi ke Keranjang screen tapi crash setelahnya*/
            Button(
                onClick = {
                    coroutineScope.launch {
                        market?.let { safeMarket ->
                            // Replace hardcoded user ID with a dynamic way of getting current user
                            // Consider using SharedPreferences or a user session management system
                            val currentUserId = 1 // TODO: Replace with actual user ID retrieval

                            // Add validation for quantity
                            val quantity = 1  // Or get from some input mechanism
                            if (quantity > 0) {
                                viewModel.addToCart(safeMarket.id_produk, currentUserId, quantity)

                                // Wait a bit to see if cart addition was successful
                                delay(500)  // Small delay to allow error state to update

                                if (viewModel.error.value == null) {
                                    navController.navigate(Screen.Keranjang.createRoute(safeMarket.id_produk))
                                } else {
                                    // Optionally show an error message to the user
                                    // You could use a Snackbar or Toast
                                    Log.e("CartError", viewModel.error.value ?: "Unknown error")
                                }
                            } else {
                                // Handle invalid quantity
                                Log.e("CartError", "Quantity must be greater than 0")
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(190.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B))
            ) {
                Text(text = "Masukkan Keranjang", color = Color.White, maxLines = 1)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onShowBottomSheetChange(true) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(190.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B8C51))
            ) {
                Text(text = "Beli Sekarang", color = Color.White)
            }
        }
    }
}

//Pengaturan Bottom Sheet Statiska (Maps Page)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomSheetBuyNow(
    modifier: Modifier = Modifier,
    showBottomSheetBuyNow: Boolean,
    onShowBottomSheetChange: (Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheetBuyNow) {
        ModalBottomSheet(
            onDismissRequest = { onShowBottomSheetChange(false) },
            sheetState = sheetState,
            modifier = modifier
        ) {
            Column (
                modifier = Modifier
                    .height(250.dp)
                    .padding(25.dp)
            ){
                Text(
                    text = "Quantity",
                    style = TextStyle (
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFF5B8C51))
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Decrease Quantity",
                            tint = Color.White
                        )
                    }
                    OutlinedTextField(
                        value = "1",
                        onValueChange = {},
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .padding(16.dp)
                            .width(60.dp)
                            .align(Alignment.CenterVertically)
                    )

                    IconButton(
                        onClick = {  },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFF5B8C51))
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Increase Quantity",
                            tint = Color.White
                        )
                    }


                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5B8C51)
                    )
                ) {
                    Text(text = "Beli Sekarang", color = Color.White)
                }
            }
        }
    }
}

