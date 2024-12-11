package com.example.agrisynergi_mobile.pages.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.outlinedButtonColors
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.navigation.Screen

@Composable
fun LoginRegistScreen(navController: NavController) {
    LoginRegistContent(navController = navController)
}

@Composable
fun LoginRegistContent(
    modifier: Modifier = Modifier,
    navController : NavController

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF13382C))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Bagian Gambar
        Image(
            painter = painterResource(id = R.drawable.imageloginregist1),
            contentDescription = "Image login regist",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        // Bagian Teks Selamat Datang
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selamat Datang di AgriSynergy!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFDCF3B),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Bergabunglah dengan komunitas petani jagung modern yang memanfaatkan teknologi untuk meningkatkan produktivitas dan pemasaran hasil panen.",
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        // Bagian Tombol login
        Column {
            Button(
                onClick = {  navController.navigate(Screen.Login.route) },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(50.dp),
                colors = buttonColors(
                    backgroundColor = Color(0xFF5B8C51),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Masuk",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bagian Tombol daftar
            OutlinedButton(
                onClick = {  navController.navigate(Screen.Regist.route) },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(50.dp),
                border = BorderStroke(2.dp, Color(0xFF5B8C51)),
                colors = outlinedButtonColors(
                    backgroundColor = Color.Transparent, 
                    contentColor = Color(0xFF5B8C51)
                )
            ) {
                Text(
                    text = "Daftar",
                    fontSize = 16.sp,
                    color = Color(0xFF5B8C51)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginRegistScreen() {
    LoginRegistScreen(navController = rememberNavController())
}
