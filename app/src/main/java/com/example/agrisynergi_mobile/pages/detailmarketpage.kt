package com.example.agrisynergi_mobile.pages

import android.media.Rating
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.datamarket

@Composable
fun DetailMarketScreen(marketId: Int, navController: NavHostController) {
    var showBottomSheetBuyNow by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DetailTopBar(
            onBackClick = { navController.navigateUp() }
        )
        ContentMarket(
            marketId = marketId,
            modifier = Modifier.weight(1f)
        )
        DetailBottomBar(
            navController = navController,
            showBottomSheet = showBottomSheetBuyNow,
            onShowBottomSheetChange = { showBottomSheetBuyNow = it },
            modifier = Modifier.fillMaxWidth()
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
fun ContentMarket(marketId: Int, modifier: Modifier = Modifier) {
    val market = datamarket.markets.find { it.id == marketId }
    if (market != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(data = market.imageUrl),
                contentDescription = "Image Market",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
                    .shadow(4.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = market.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = market.description,
                fontSize = 12.sp,
                color = Color.Gray,
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
                rating = 3.5,
                starsColor = Color.Yellow,
                onRatingChanged = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = market.price,
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
                text = market.productDetail,
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
    onShowBottomSheetChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
//                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { navController.navigate("forumPage") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(190.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F897B))
            ) {
                Text(text = "Masukkan Keranjang", color = Color.White)
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

