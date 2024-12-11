package com.example.agrisynergi_mobile.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
    var selectedOption by remember { mutableStateOf<Int?>(null) }
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
            Column(
                modifier = Modifier
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .background(colorResource(R.color.white))
                    .fillMaxWidth()
                    .shadow(elevation = 0.1.dp),
                verticalArrangement = Arrangement.Center
            ) {
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
                    KonfirmasiButton(
                        navController = navController,
                        selectedOption = selectedOption
                    )
                }}
            }
        }
    ) {
        paddingValues ->
        OpsiPengiriman(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            selectedOption = selectedOption,
            onOptionSelected = { option ->
                selectedOption = option
            }
        )
    }
}

@Composable
fun OpsiPengiriman(
    navController: NavHostController,
    modifier: Modifier,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        OptionCard(
            title = "Antar Ke Counter",
            description = "Anda dapat mengirimkan paket anda di Cabang J&T Express terdekat di kota Anda",
            icon = R.drawable.ic_antarkecounter,
            backgroundColor = if (selectedOption == 0) Color(0xFF5B8C51) else Color.White,
            contentColor = if (selectedOption == 0) Color.White else Color.Black,
            onClick = { onOptionSelected(0) },
        )

        OptionCard(
            title = "Pickup",
            description = "Kurir Q&T Express akan mengambil paket dari alamat anda",
            icon = R.drawable.ic_pickup,
            backgroundColor = if (selectedOption == 1) Color(0xFF5B8C51) else Color.White,
            contentColor = if (selectedOption == 1) Color.White else Color.Black,
            onClick = { onOptionSelected(1) }
        )
    }
}

@Composable
fun OptionCard(
    title: String,
    description: String,
    icon: Int,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RectangleShape)
            .background(backgroundColor, shape = RectangleShape)
            .clickable { onClick() }
            .padding(16.dp)
            .height(120.dp),
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
                fontSize = 18.sp,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = contentColor,
                fontWeight = FontWeight.Normal,
                lineHeight = 12.sp
            )
        }
    }
}

@Composable
fun KonfirmasiButton(
    navController: NavHostController,
    selectedOption: Int?
) {
    Button(
        onClick = {
            if (selectedOption == 0) {
                navController.navigate("counter")
            } else if (selectedOption == 1) {
                navController.navigate("pickup")
            }
        },
        shape = RoundedCornerShape(
            corner = CornerSize(10.dp)),
            colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.hijau_pudar)),
        modifier = Modifier.fillMaxWidth().height(50.dp)
    ) {
        Text(text = "Konfirmasi")
    }
}