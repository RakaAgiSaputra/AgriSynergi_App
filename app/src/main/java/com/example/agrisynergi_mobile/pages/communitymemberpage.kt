package com.example.agrisynergi_mobile.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.database.ModelKomunitas.UserViewModel
import com.example.agrisynergi_mobile.retrofit.model.User

@Composable
fun CommunityMemberScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val users by remember { derivedStateOf { userViewModel.users } }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        userViewModel.getUsers()
        isLoading = false
    }

    Surface(
        modifier = Modifier.background(Color.White)
    ) {
        Column {
            TopBarCommunity(onBackClick = { navController.navigateUp() })
            if (isLoading) {
                // Loading state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading...",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    )
                }
            } else {
                CommunityList(users = users)
            }
        }
    }
}

@Composable
fun TopBarCommunity(onBackClick: () -> Unit) {
    TopAppBar(backgroundColor = Color(0xFF13382C)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.iconback),
                    contentDescription = "Icon Back",
                    tint = Color.White
                )
            }
            Text(
                text = "Anggota",
                style = TextStyle(fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            )
            IconButton(onClick = { /*TODO: Implement search functionality */ }) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.iconsearch),
                    contentDescription = "Icon Search",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun CommunityList(users: List<User>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(users) { user ->
            ContentCommunity(user = user)
        }
    }
}

@Composable
fun ContentCommunity(user: User?) { // Make sure user can be null
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Check if user is not null and proceed with image loading
        val imageUrl = user?.foto?.let { "https://gtk62vzp-3000.asse.devtunnels.ms/api/fileUsers/$it" }

        // Remember the image painter with an error fallback
        val imagePainter = rememberAsyncImagePainter(
            model = imageUrl,
            error = painterResource(id = R.drawable.imagenotavail)
        )

        Image(
            painter = imagePainter,
            contentDescription = "User Photo",
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(15.dp))

        // If user is not null, show their name and email
        user?.let {
            Column {
                Text(
                    text = it.nama,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                )
                Text(
                    text = it.email,
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }
        }
    }
}


