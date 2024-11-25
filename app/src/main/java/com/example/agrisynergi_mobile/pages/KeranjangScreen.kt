package com.example.agrisynergi_mobile.pages

import android.adservices.adid.AdId
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.datamarket
import com.example.agrisynergi_mobile.navigation.Screen

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable


fun KeranjangScreen(marketId: Int, navController: NavController){
    Scaffold (
        topBar = {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().height(55.dp).
                background(colorResource(R.color.hijau_tua)),
                verticalAlignment = Alignment.CenterVertically) {
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

                Text(text = "Keranjang",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = colorResource(R.color.white))

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
                .background(colorResource(R.color.white))
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Center) {
                Row(horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 40.dp, max = 60.dp)
                        .padding(10.dp)) {
                    Text(text = "Voucer Aplikasi", fontSize = 12.sp)
                    Row (horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()){
                        Text(text = "-Rp.80.000", color = colorResource(R.color.white),
                            modifier = Modifier.background(colorResource(R.color.hijau_pudar)),
                            fontSize = 12.sp)
                        Text(text = "Voucer Gratis Ongkir", color = colorResource(R.color.white),
                            modifier = Modifier.background(colorResource(R.color.hijau_pudar)),
                            fontSize = 12.sp)
                    }
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.abu))
                    .height(1.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp)
                ) {
                    val totalHarga = 150000f
                    Text(text = "Total harga  Rp ${String.format("%,.0f", totalHarga)}", fontWeight = FontWeight.Bold)
                    Button(onClick = {
                        navController.navigate(Screen.BeliSekarang.createRoute(marketId))
                    }, shape = RoundedCornerShape(
                        corner = CornerSize(10.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.hijau_pudar)                          )
                    ) {
                        Text("Beli Sekarang")
                    }
                }
            }
        }
    ){
            paddingValues -> ListBelanja(marketId,Modifier.padding( paddingValues))
    }

}

@Composable
fun ListBelanja(marketId: Int, modifier: Modifier){
    LazyColumn(modifier = modifier) {
        items(1){
            ItemListBelanja(marketId)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ItemListBelanja(marketId: Int){
    var isCheckedStore by remember { mutableStateOf(false) }
    var isCheckedStoreItem by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }
    val market = datamarket.markets.find { it.id == marketId }



    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Row (verticalAlignment = Alignment.CenterVertically){
            Checkbox(checked = isCheckedStore, onCheckedChange = {
                isCheckedStore = it
                isCheckedStoreItem = it }, colors = CheckboxDefaults.colors(
                checkedColor = colorResource(R.color.black),
                uncheckedColor = colorResource(R.color.abu),
                checkmarkColor = colorResource(R.color.white)

            ))
            Text(text="Star", color = colorResource(R.color.white),
                modifier = Modifier
                    .background(color = colorResource(R.color.hijau_terang))
                    .padding(start = 8.dp, end = 8.dp))
            Text(text= "Surya Bangunan", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
        }


        Row (verticalAlignment = Alignment.CenterVertically){
            Checkbox(checked = isCheckedStoreItem, onCheckedChange = {isCheckedStoreItem = it}, colors = CheckboxDefaults.colors(
                checkedColor = colorResource(R.color.black),
                uncheckedColor = colorResource(R.color.abu),
                checkmarkColor = colorResource(R.color.white)

            ))
            Image(
                painter = rememberAsyncImagePainter(market?.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp).clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            )

            Spacer(Modifier.width(10.dp))

            Column {
                Text(text = market!!.title)
                Box(modifier = Modifier.padding(end = 8.dp)){
                    Row (modifier = Modifier
                        .background(colorResource(R.color.hijau_pudar))
                        .height(25.dp),
                        verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Variasi: warna Merah", fontSize = 12.sp, color = colorResource(R.color.white))
                        IconButton(onClick = {}) {
                            Icon(painter = painterResource(R.drawable.ic_downdown_r),
                                contentDescription = null,
                                tint = colorResource(R.color.white)
                            )
                        }
                    }
                }
                val hargaItems = 200000f
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough)
                        ){
                            append("Rp ${String.format("%,.0f", hargaItems)}")
                        }

                        withStyle(style = SpanStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = colorResource(R.color.hijau_terang),
                        )){
                            val afterDiscount = (hargaItems * 0.25)
                            append(" ${String.format("%,.0f", hargaItems - afterDiscount)}")
                        }
                    },
                    modifier = Modifier.padding(top = 8.dp, end = 8.dp)
                )

                Row (modifier = Modifier.fillMaxWidth()){
                    Box(modifier = Modifier
                        .padding(end = 8.dp)
                        .background(colorResource(R.color.hijau_pudar))
                        .size(26.dp)){
                        IconButton(
                            onClick = {
                                if (counter > 0) counter++
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_remove_icon_r),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                    Box (modifier = Modifier
                        .padding(end = 8.dp)
                        .width(36.dp)
                        .height(27.dp)
                        .border(
                            shape = RoundedCornerShape(
                                CornerSize(8.dp)
                            ), width = 2.dp, color = Color.Gray
                        )
                        .padding(3.dp)){
                        Text(text = counter.toString(), fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    }

                    Box(modifier = Modifier
                        .padding(end = 8.dp)
                        .background(colorResource(R.color.hijau_pudar))
                        .size(26.dp)){
                        IconButton(
                            onClick = {
                                counter++
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))

            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.abu))
            .height(1.dp))

        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()){
            Image(
                painter = painterResource(R.drawable.ic_baseline_discount_r),
                contentDescription = null,
                modifier = Modifier.size(35.dp).padding(10.dp)
            )

            Text(text="Tambah Rp56,1RB dapat diskon Rp1,5Rb", fontSize = 12.sp)

            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_leftleft),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.abu))
            .height(1.dp))
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()){
            Image(
                painter = painterResource(R.drawable.img_gratis_ongkir),
                contentDescription = null,
                modifier = Modifier.size(50.dp).padding(10.dp)
            )

            Text(text="Gratis ongkir s/d Rp10.000 dengan min. belanja Rp0 \nGratis Ongkir s/d Rp250.000 dengan min. ...", fontSize = 10.sp)

            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_leftleft),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp).padding(end = 10.dp)
                )

            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.abu))
            .height(1.dp))
    }
}