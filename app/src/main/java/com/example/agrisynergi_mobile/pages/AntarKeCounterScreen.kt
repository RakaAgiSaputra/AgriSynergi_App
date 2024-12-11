package com.example.agrisynergi_mobile.pages

import android.annotation.SuppressLint
import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.datamarket

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(navController: NavController){
    Scaffold (
        topBar = {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().height(55.dp).
                background(colorResource(R.color.hijau_tua)),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Icon(
                        painterResource(R.drawable.ic_leftleft),
                        contentDescription = null,
                        tint = colorResource(R.color.white)
                    )
                }

                Text(text = "Rincian Antar ke Counter",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = colorResource(R.color.white))

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .background(colorResource(R.color.white))
                    .fillMaxWidth()
                    .shadow(elevation = 0.1.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .background(colorResource(R.color.white))
                    .fillMaxWidth().shadow(elevation = 0.1.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Button(
                            onClick = { navController.navigate("beranda") }, shape = RoundedCornerShape(
                                corner = CornerSize(10.dp)),
                                colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.hijau_pudar)),
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("OK", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }}
    ){
            paddingValues ->  AntarKeCounterScreen(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun AntarKeCounterScreen(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        Card(
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Q&T Express No. Resi",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.img_jagung_original),
                    contentDescription = "Q&T Express Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "JP421654820",
                    fontSize = 28.sp,
                    color = Color(color = 0xFF5B8C51),
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
        }
        Card(
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)){
                Row {
                    Text(text = "Persiapkan paketmu sebelum kurir datang dengan mengikuti petunjuk berikut:", fontSize = 14.sp, lineHeight = 14.sp, softWrap = true, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(5.dp))
            Box {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                ) {
                    Row {
                        Text(text = "1.", fontSize = 14.sp, softWrap = true)
                        Spacer(Modifier.width(12.dp))
                        Text(text = "Cetak dan tempelkan label pengiriman dengan no. resi otomatis ke paketmu", fontSize = 14.sp, lineHeight = 14.sp, softWrap = true)
                    }
                    Spacer(Modifier.height(12.dp))
                    Row {
                        Text(text = "2.", fontSize = 14.sp, softWrap = true)
                        Spacer(Modifier.width(12.dp))
                        Text(text = "Drop off paketmu di counter yang terletak di kecamatan yang sama dengan alamat tokomu. " +
                                "Drop off di selain kecamatan tersebut berpotensi menyebabkan selisih ongkir yang dibebankan " +
                                "kepada penjual.", fontSize = 14.sp, lineHeight = 14.sp, softWrap = true)
                    }
                    Spacer(Modifier.height(12.dp))
                    Row {
                        Text(text = "3.", fontSize = 14.sp, softWrap = true)
                        Spacer(Modifier.width(12.dp))
                        Text(text = "Mohon untuk tidak membayar biaya pengiriman " +
                                "apapun saat drop off di counter", fontSize = 14.sp, lineHeight = 14.sp, softWrap = true)
                    }
                }
            }
        }
    }
}


//            }
//        }
//    }
//}