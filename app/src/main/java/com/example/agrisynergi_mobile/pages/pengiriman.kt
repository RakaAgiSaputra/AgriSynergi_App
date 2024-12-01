package com.example.agrisynergi_mobile.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R

@Composable
fun PengirimanScreen( navController: NavHostController) {
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

                Text(text = "Atur Pengiriman",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = colorResource(R.color.white)
                )

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                }
            }
        },
        bottomBar = {
            Column(modifier = Modifier
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .background(colorResource(R.color.white))
                .fillMaxWidth().shadow(elevation = 0.1.dp),
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
                    Button( onClick = { navController.navigate("pickup") }, shape = RoundedCornerShape(
                        corner = CornerSize(10.dp)
                    ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.hijau_pudar)
                        ), modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Konfirmasi", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ){
            paddingValues -> OpsiPengiriman(navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun OpsiPengiriman(navController: NavHostController, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {

        OptionCard(
            title = "Antar Ke Counter",
            description = "Anda dapat mengirimkan paket anda di Cabang J&T Express terdekat di kota Anda",
            icon = R.drawable.ic_antarkecounter,
            backgroundColor = Color.White,
            contentColor = Color.Black
        )

        OptionCard(
            title = "Pickup",
            description = "Kurir Q&T Express akan mengambil paket dari alamat anda",
            icon = R.drawable.ic_pickup,
            backgroundColor = Color(0xFF5B8C51),
            contentColor = Color.White
        )
    }
}

@Composable
fun OptionCard(
    title: String,
    description: String,
    icon: Int, // Resource ID untuk ikon
    backgroundColor: Color,
    contentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor,
                shape = androidx.compose.ui.graphics.RectangleShape)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                color = contentColor
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = contentColor
            )
        }
    }
}

@Composable
fun KonfirmasiButton() {
    Button(
        onClick = { /* Handle Confirm Action */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF5B8C51),
            contentColor = Color.White
        )
    ) {
        Text(text = "Konfirmasi")
    }
}