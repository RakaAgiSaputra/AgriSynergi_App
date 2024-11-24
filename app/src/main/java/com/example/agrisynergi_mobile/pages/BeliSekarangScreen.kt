package com.example.agrisynergi_mobile.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.datamarket
import com.example.agrisynergi_mobile.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DefaultLocale")
@Composable
fun BeliScreen( marketId: Int, navController: NavController){
    Scaffold (
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().height(55.dp)
                    .background(colorResource(R.color.hijau_tua)),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {navController.navigateUp()}
                ) {
                    Icon(
                        painterResource(R.drawable.ic_leftleft),
                        contentDescription = null,
                        tint = colorResource(R.color.white)
                    )
                }

                Text(
                    text = "Beli Sekarang",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = colorResource(R.color.white)
                )
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painterResource(R.drawable.ic_shopping_bag_r),
                        contentDescription = null,
                        tint = colorResource(R.color.white)
                    )
                }
            }
        },

        bottomBar = {
            Column(modifier = Modifier
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .background(colorResource(R.color.white))
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Center) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    val totalHarga = 50000f
                    Text(text = "Rp ${String.format("%,.0f", totalHarga)}", modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Bold)
                    Button(onClick = {
                        navController.navigate(Screen.Checkout.createRoute(marketId))
                    }, shape = RoundedCornerShape(
                        corner = CornerSize(10.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.hijau_pudar)), modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Checkout")
                    }
                }
            }
        }
    ){
            paddingValues ->  BeliSekarangScreen(marketId, Modifier.padding(paddingValues))
    }
}

@Composable
fun BeliSekarangScreen(marketId: Int,modifier: Modifier){
    val market = datamarket.markets.find { it.id == marketId }

//    val dot_merah = Text("", modifier = Modifier.border(shape = RoundedCornerShape(corner = CornerSize(10.dp)), width = 10.dp, color = Color.Red).size(width = 8.dp, height =8.dp))
//    val dot_hijau = Text("", modifier = Modifier.border(shape = RoundedCornerShape(corner = CornerSize(10.dp)), width = 10.dp, color = Color.Green).size(width = 8.dp, height =8.dp))

    Column (modifier = modifier){
        Image(
            painter = rememberAsyncImagePainter(market?.imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(300.dp).align(Alignment.CenterHorizontally).padding(bottom = 0.dp)
        )
Box(modifier = Modifier.fillMaxSize().background(colorResource(R.color.white)).padding(start = 18.dp)){

        Column(modifier = Modifier.fillMaxSize()) {
            Text("\"${market?.title}\"", fontWeight = FontWeight.Bold)
            Text(text = market!!.description)
            Text(text= market.price, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_archive_r),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Column {
                    Text(text = "Free pick-up in store")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "",
                            modifier = Modifier.padding(end = 8.dp).border(
                                shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                                width = 10.dp,
                                color = Color.Red
                            ).size(width = 8.dp, height = 8.dp)
                        )
                        Text(text = "Item unavailable")
                    }

                }
                Box(
                    modifier = Modifier.border(
                        color = colorResource(R.color.black),
                        width = 2.dp,
                        shape = RoundedCornerShape(
                            CornerSize(8.dp)
                        )
                    ).padding(12.dp)
                ) {
                    Row {
                        Image(
                            painter = painterResource(R.drawable.ic_notification_bell_r),
                            contentDescription = null
                        )
                        Text("Notify me")
                    }
                }
            }
            //        Spacer(Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_truck_r),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(text = "Delivery in up to 3 days")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "",
                            modifier = Modifier.padding(end = 8.dp).border(
                                shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                                width = 10.dp,
                                color = Color.Green
                            ).size(width = 8.dp, height = 8.dp)
                        )
                        Text(text = "Item available")
                    }

                }
            }
            //        Spacer(Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_rotate_r),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(text = "Free return in up to 30 days")
                }
            }
            //        Spacer(Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_credit_card_r),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(text = "Secure pay")
                }
            }
        }

        }

    }
}
