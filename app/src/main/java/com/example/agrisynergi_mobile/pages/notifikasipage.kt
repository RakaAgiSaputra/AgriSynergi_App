package com.example.agrisynergi_mobile.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun NotifScreen(navController: NavHostController) {
//    val retrofit = RetrofitClient().instance

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            NotifTopBar(
                onBackClick = { navController.navigateUp() }
            )
//            NotifContent(api = retrofit)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotifTopBar(
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
            androidx.compose.material.Text(
                text = "Notifikasi",
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
                    painter = painterResource(id = R.drawable.iconback),
                    contentDescription = "Icon Keranjang",
                    tint = Color(0xFF13382C)
                )
            }
        }
    }
}


//(hanya contoh) bisa dihapus Penerapan database untuk untuk user di halaman notifikasi
//@Composable
//fun NotifContent(api: Api) {
//    val usersData = remember { mutableStateOf<List<User>>(emptyList()) }
//    val errorMessage = remember { mutableStateOf("") }
//    val coroutineScope = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        coroutineScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    api.getUsers().execute()
//                }
//                if (response.isSuccessful) {
//                    response.body()?.let { body ->
//                        usersData.value = body.data
//                    } ?: run {
//                        errorMessage.value = "Response body is null"
//                    }
//                } else {
//                    errorMessage.value = "Error: ${response.code()} - ${response.message()}"
//                }
//            } catch (e: Exception) {
//                errorMessage.value = "Gagal memuat data: ${e.localizedMessage}"
//            }
//        }
//    }
//
//    if (usersData.value.isNotEmpty()) {
//        LazyColumn(modifier = Modifier.padding(16.dp)) {
//            items(usersData.value) { user ->
//                Column(modifier = Modifier.padding(bottom = 16.dp)) {
//                    androidx.compose.material.Text(
//                        text = "Nama: ${user.name}",
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//                    androidx.compose.material.Text(
//                        text = "Email: ${user.email}"
//                    )
//                    androidx.compose.material.Text(
//                        text = "Number: ${user.phoneNumber}"
//                    )
//                }
//            }
//        }
//    } else if (errorMessage.value.isNotEmpty()) {
//        androidx.compose.material.Text(
//            text = errorMessage.value,
//            color = Color.Red
//        )
//    } else {
//        androidx.compose.material.Text(
//            text = "Memuat...",
//            color = Color.Gray
//        )
//    }
//}
//
