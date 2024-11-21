package com.example.agrisynergi_mobile.User

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.shadow
import com.example.agrisynergi_mobile.User.DoneCheckoutActivity
import com.example.agrisynergi_mobile.User.DropshipperCatalog2Activity

import com.example.agrisynergi_mobile.R

class UserProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserProfileScreen { option ->
                when (option) {
                    "Edit Profile" -> {
                        startActivity(Intent(this, EditProfileActivity::class.java))
                    }
                    "Riwayat Pesanan" -> {
                        startActivity(Intent(this, OrderHistoryActivity::class.java))
                    }
                    "Tentang Kami" -> {
                        startActivity(Intent(this, AboutUsActivity::class.java))
                    }
                    "Katalog Dropshipper" -> {
                        startActivity(Intent(this, DropshipperCatalogActivity::class.java))
                    }
                    "Katalog Dropshipper2" -> {
                        startActivity(Intent(this, DropshipperCatalog2Activity::class.java))
                    }
                    "Postingan Saya" -> {
                        startActivity(Intent(this, PostingActivity::class.java))
                    }
                    "Favorite Saya" -> {
                        startActivity(Intent(this, MyFavoriteActivity::class.java))
                    }
                    "Favorite2 Saya" -> {
                        startActivity(Intent(this, MyFavorite2Activity::class.java))
                    }
                    "Mulai Berjualan" -> {
                        startActivity(Intent(this, StartSellingActivity::class.java))
                    }
                    "Masukkan Diskon" -> {
                        startActivity(Intent(this, AddDiscountCodeActivity::class.java))
                    }
                    "Done Checkout" -> {
                        startActivity(Intent(this, DoneCheckoutActivity::class.java))
                    }
                    "Checkout" -> {
                        startActivity(Intent(this, CheckoutActivity::class.java))
                    }

                }
            }
        }
    }
}


@Composable
fun UserProfileScreen(onOptionSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            UserProfileHeader()
            ProfileSection()
            OptionsList(onOptionSelected)
            Spacer(modifier = Modifier.height(60.dp))
        }

        BottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun UserProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "User",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

    }
}

@Composable
fun ProfileSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF5F897B), RoundedCornerShape(12.dp))
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.pak_tani),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text("Asep Sarafuddin", fontSize = 16.sp, color = Color(0xFF333333), fontWeight = FontWeight.Medium)
            Text("asep123@gmail.com", fontSize = 14.sp, color = Color(0xFF777777))
            Text("Jakarta, Indonesia", fontSize = 14.sp, color = Color(0xFF777777))
        }
    }
}


@Composable
fun OptionsList(onOptionSelected: (String) -> Unit) {
    val options = listOf(
        "Edit Profile",
        "Riwayat Pesanan",
        "Postingan Saya",
        "Katalog Dropshipper",
        "Favorite Saya",
        "Mulai Berjualan",
        "Jadi Dropshipper",
        "Tentang Kami",
        "Log Out",
        "Katalog Dropshipper2",
        "Favorite2 Saya",
        "Masukkan Diskon",
        "Done Checkout",
        "Checkout",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false
            )
            .background(Color(0xFF5F897B), RoundedCornerShape(12.dp))
            .padding(vertical = 16.dp)
    ) {
        options.forEach { option ->
            OptionItem(option = option, onOptionSelected = onOptionSelected)
            if (option == "Jadi Dropshipper" || option == "Log Out") {
                Divider(
                    color = Color(0xFFF0F2F5),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.width(16.dp))
}

@Composable
fun OptionItem(option: String, onOptionSelected: (String) -> Unit) {
    var isClicked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isClicked = true
                onOptionSelected(option)
            }
            .background(if (isClicked) Color(0xFFBCD7BC) else Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 12.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = option,
            color = Color.White,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        if (option != "Tentang Kami" && option != "Log Out") {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }

    LaunchedEffect(isClicked) {
        if (isClicked) {
            kotlinx.coroutines.delay(200)
            isClicked = false
        }
    }
}



@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BottomNavItem("Beranda", R.drawable.ic_beranda, isSelected = false)
        BottomNavItem("Maps", R.drawable.ic_maps, isSelected = false)
        BottomNavItem("Market", R.drawable.ic_market, isSelected = false)
        BottomNavItem("Consultation", R.drawable.ic_consultation, isSelected = false)
        BottomNavItem("User", R.drawable.ic_profile, isSelected = true)
    }
}

@Composable
fun BottomNavItem(label: String, iconRes: Int, isSelected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = if (isSelected) Color(0xFF5F897B) else Color.White,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = if (isSelected) Color(0xFF5F897B) else Color.White,
            fontSize = 12.sp
        )
    }
}
