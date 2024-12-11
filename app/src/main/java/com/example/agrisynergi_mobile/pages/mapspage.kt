package com.example.agrisynergi_mobile.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.database.DatabaseMaps.Sawah
import com.example.agrisynergi_mobile.database.RetrofitClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.Marker


//MapsScreen Utama
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val placesClient = Places.createClient(context)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-7.250445, 112.768845), 10f) // Jawa Timur Coordinates
    }

    var sawahData by remember { mutableStateOf<List<Sawah>>(emptyList()) }

    // Fetch Sawah data on composition
    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.apiService.getSawahData()
            if (response.success) {
                sawahData = response.data
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error fetching data: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Peta dengan Marker
        MyMapWithMarker(sawahData = sawahData) // Pass sawahData here

        // Lapisan bar atas
        mapsTopBar(
            onBackClick = { navController.navigateUp() },
            onSearchClick = { query -> /* Handle search */ },
            modifier = Modifier.zIndex(1f)
        )

        // Lapisan bar bawah (optional)
//        if (showBottomSheetStatistic) {
//            BottomSheetStatistic(onDismiss = { showBottomSheetStatistic = false })
//        }
    }
}




@Composable
fun MyMapScreen( ) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        onMapLoaded = {
// Peta telah dimuat, lakukan sesuatu jika diperlukan

        }
    )
}

@Composable
fun MyMapWithMarker(sawahData: List<Sawah>) {
    // State for camera position
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-7.250445, 112.768845), 10f) // Default to Surabaya
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // Add markers based on the sawah data
        sawahData.forEach { sawah ->
            val latLng = LatLng(sawah.latitude.toDouble(), sawah.longitude.toDouble())
            Marker(
                state = MarkerState(position = latLng),
                title = sawah.lokasi,
                snippet = sawah.deskripsi,
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED) // Optional: Set custom marker color
            )
        }
    }
}


//Pengaturan lokasi
@Composable
fun ZoomControls(onZoomIn: () -> Unit, onZoomOut: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.Transparent)
            .padding(4.dp)
    ) {
        IconButton(onClick = onZoomIn) {
            Icon(
                painter = painterResource(id = R.drawable.iconzoomin),
                contentDescription = "Zoom In",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
        }
        IconButton(onClick = onZoomOut) {
            Icon(
                painter = painterResource(id = R.drawable.iconzoomout),
                contentDescription = "Zoom Out",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}


//Pengaturan top bar Maps
@Composable
fun mapsTopBar(
    onBackClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("Maps Screen") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { onSearchClick("Example Search Query") }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        },
        modifier = modifier
    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetStatistic(onDismiss: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() }
    ) {
        Text(
            text = "Informasi Statistik",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}


//Pengaturan Bottom Bar Maps
@Composable
fun mapsBottomBar(
    navController: NavHostController,
    showBottomSheet: Boolean,
    onShowBottomSheetChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { onShowBottomSheetChange(true) },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF13382C))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icondiagram),
                    contentDescription = "Statistik",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Statistik", color = Color.White)
            }
        }
    }
}


//
////Pengaturan bottom shet untuk icon marking lokasi jagung
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BottomSheetMarking(
//    modifier: Modifier = Modifier,
//    showBottomSheetMarking: Boolean,
//    onShowBottomSheetChange: (Boolean) -> Unit,
//    selectedLocation: GeoPoint?
//) {
//    val sheetState = rememberModalBottomSheetState()
//
//    if (showBottomSheetMarking) {
//        ModalBottomSheet(
//            onDismissRequest = { onShowBottomSheetChange(false) },
//            sheetState = sheetState,
//            modifier = Modifier
//        ) {
//            Column (
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//                    .padding(16.dp)
//            ){
//                Box(
//                    modifier = Modifier
//                        .shadow(5.dp, shape = RoundedCornerShape(16.dp))
//                        .background(Color(0xFF13382C), shape = RoundedCornerShape(16.dp))
//                        .padding(16.dp)
//                        .align(Alignment.CenterHorizontally)
//                        .width(300.dp)
//                        .height(300.dp)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(1.dp)
//                    ) {
//                        Row {
//                            Box() {
//                                Spacer(modifier = Modifier.height(20.dp))
//                                Image (
//                                    painter = painterResource(id = R.drawable.profilepetani),
//                                    contentDescription = "profil Petani",
//                                    modifier = Modifier
//                                        .size(65.dp)
//                                        .clip(CircleShape)
//                                        .align(Alignment.Center),
//                                    contentScale = ContentScale.Crop
//                                )
//                            }
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Column (
//                                modifier = Modifier
//                                    .padding(3.dp),
////                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .width(350.dp)
//                                        .height(40.dp)
//                                        .background(Color.Transparent)
//                                        .padding(2.dp)
//                                ) {
//                                    Row(
//                                        modifier = Modifier.fillMaxSize(),
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = "Alamat",
//                                            style = TextStyle(
//                                                fontSize = 9.sp,
//                                                color = Color.White
//                                            )
//                                        )
//                                        Spacer(modifier = Modifier.width(15.dp))
//                                        Text(
//                                            text = "Jl. Raya Bagusan, RT.5/RW.29, Bagusan, Terusan, Kec. Gedeg, Kabupaten Mojokerjo, Jawa Timur 613451",
//                                            style = TextStyle(
//                                                fontSize = 9.sp,
//                                                color = Color.White
//                                            )
//                                        )
//                                    }
//                                }
//                                Box(
//                                    modifier = Modifier
//                                        .width(350.dp)
//                                        .height(20.dp)
//                                        .background(Color.Transparent)
//                                        .padding(2.dp)
//                                ) {
//                                    Row(
//                                        modifier = Modifier.fillMaxSize(),
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = "Jam",
//                                            style = TextStyle(
//                                                fontSize = 9.sp,
//                                                color = Color.White
//                                            )
//                                        )
//                                        Spacer(modifier = Modifier.width(25.dp))
//                                        Text(
//                                            text = "Tutup - Buka Senin Pukul 06.00",
//                                            style = TextStyle(
//                                                fontSize = 9.sp,
//                                                color = Color.White
//                                            )
//                                        )
//                                    }
//                                }
//                                Box(
//                                    modifier = Modifier
//                                        .width(350.dp)
//                                        .height(20.dp)
//                                        .background(Color.Transparent)
//                                        .padding(2.dp)
//                                ) {
//                                    Row(
//                                        modifier = Modifier.fillMaxSize(),
////                                        horizontalArrangement = Arrangement.SpaceBetween,
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = "Telepon",
//                                            style = TextStyle(
//                                                fontSize = 9.sp,
//                                                color = Color.White
//                                            )
//                                        )
//                                        Spacer(modifier = Modifier.width(15.dp))
//                                        Text(
//                                            text = "0897-1234-2345",
//                                            style = TextStyle(
//                                                fontSize = 9.sp,
//                                                color = Color.White
//                                            )
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                        Spacer(modifier = Modifier.height(15.dp))
//                        Column(
//                        ) {
//                            Text(
//                                text = "Produksi Jagung",
//                                style = TextStyle(
//                                    fontSize = 14.sp,
//                                    fontWeight = FontWeight.SemiBold,
//                                    color = Color.White
//                                )
//                            )
//                            Text(
//                                text = "Total produksi jagung di daerah ini mencapai 8,500 ton pada tahun 2024, menunjukan peningkatan sebesar 5% dibandingkan tahun sebelumnya.",
//                                style = TextStyle(
//                                    fontSize = 12.sp,
//                                    color = Color.White
//                                )
//                            )
//                            Spacer(modifier = Modifier.height(15.dp))
//                            Text(
//                                text = "Produksi Jagung",
//                                style = TextStyle(
//                                    fontSize = 14.sp,
//                                    fontWeight = FontWeight.SemiBold,
//                                    color = Color.White
//                                )
//                            )
//                            Text(
//                                text = "Total produksi jagung di daerah ini mencapai 8,500 ton pada tahun 2024, menunjukan peningkatan sebesar 5% dibandingkan tahun sebelumnya.",
//                                style = TextStyle(
//                                    fontSize = 12.sp,
//                                    color = Color.White
//                                )
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//}


//
////Pengaturan Bottom Sheet Statiska (Maps Page)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun bottomSheetStatistic(
//    modifier: Modifier = Modifier,
//    showBottomSheetStatistic: Boolean,
//    onShowBottomSheetChange: (Boolean) -> Unit
//) {
//    val sheetState = rememberModalBottomSheetState()
//
//    if (showBottomSheetStatistic) {
//        ModalBottomSheet(
//            onDismissRequest = { onShowBottomSheetChange(false) },
//            sheetState = sheetState,
//            modifier = modifier
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//                    .padding(16.dp)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .shadow(5.dp, shape = RoundedCornerShape(16.dp))
//                        .background(Color.White, shape = RoundedCornerShape(16.dp))
//                        .padding(16.dp)
//                        .align(Alignment.CenterHorizontally)
//                        .width(300.dp)
//                        .height(200.dp)
//                ) {
//                    Column {
//                        Box(
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = "FARM",
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold,
//                                textAlign = TextAlign.Center,
//                                modifier = Modifier.fillMaxWidth()
//                            )
//                        }
//
//                        Spacer(modifier = Modifier.height(15.dp))
//                        Row (
//                            modifier = Modifier.fillMaxSize(),
//                            horizontalArrangement = Arrangement.SpaceEvenly
//                        ){
//                            Image(
//                                painter = painterResource(id = R.drawable.iconrounddiagram),
//                                contentDescription = "Diagram Statistik",
//                                modifier = Modifier
//                                    .size(150.dp)
//                                    .padding(16.dp),
//
//                                )
//                            Column (
//                                modifier = Modifier
//                                    .padding(16.dp),
//                                verticalArrangement = Arrangement.Center
//                            ){
//                                Box(
//                                    modifier = Modifier
//                                        .width(120.dp)
//                                        .height(35.dp)
//                                        .background(Color(0xFFECFFF2), shape = RoundedCornerShape(8.dp))
//                                        .padding(4.dp)
//                                ) {
//                                    Row(
//                                        modifier = Modifier.fillMaxSize(),
//                                        horizontalArrangement = Arrangement.SpaceBetween,
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = "General",
//                                            style = TextStyle(
//                                                fontSize = 13.sp,
//                                                color = Color(0xFF006D1F)
//                                            )
//                                        )
//                                        Text(
//                                            text = "$8,800",
//                                            style = TextStyle(
//                                                fontSize = 13.sp,
//                                                color = Color(0xFF006D1F)
//                                            )
//                                        )
//                                    }
//                                }
//
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Box(
//                                    modifier = Modifier
//                                        .width(130.dp)
//                                        .height(35.dp)
//                                        .background(Color(0xFFECFFF2), shape = RoundedCornerShape(8.dp))
//                                        .padding(4.dp)
//                                ) {
//                                    Row(
//                                        modifier = Modifier.fillMaxSize(),
//                                        horizontalArrangement = Arrangement.SpaceBetween,
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = "Vehicle",
//                                            style = TextStyle(
//                                                fontSize = 13.sp,
//                                                color = Color(0xFF006D1F)
//                                            )
//                                        )
//                                        Text(
//                                            text = "$6,800",
//                                            style = TextStyle(
//                                                fontSize = 13.sp,
//                                                color = Color(0xFF006D1F)
//                                            )
//                                        )
//                                    }
//                                }
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Box(
//                                    modifier = Modifier
//                                        .width(120.dp)
//                                        .height(35.dp)
//                                        .background(Color(0xFFECFFF2), shape = RoundedCornerShape(8.dp))
//                                        .padding(4.dp)
//                                ) {
//                                    Row(
//                                        modifier = Modifier.fillMaxSize(),
//                                        horizontalArrangement = Arrangement.SpaceBetween,
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = "AutoMap",
//                                            style = TextStyle(
//                                                fontSize = 13.sp,
//                                                color = Color(0xFF006D1F)
//                                            )
//                                        )
//                                        Text(
//                                            text = "$1,800",
//                                            style = TextStyle(
//                                                fontSize = 13.sp,
//                                                color = Color(0xFF006D1F)
//                                            )
//                                        )
//                                    }
//                                }
//
//                            }
//                        }
//                    }
//                }
//                Spacer(modifier = Modifier.height(15.dp))
//                Box(
//                    modifier = Modifier
//                        .shadow(5.dp, shape = RoundedCornerShape(16.dp))
//                        .background(Color(0xFF13382C), shape = RoundedCornerShape(16.dp))
//                        .padding(16.dp)
//                        .align(Alignment.CenterHorizontally)
//                        .width(300.dp)
//                        .height(200.dp)
//                ) {
//                    Column (
//                    ) {
//                        Text(
//                            text = "Produksi Jagung",
//                            style = TextStyle(
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                color = Color.White
//                            )
//                        )
//                        Text(
//                            text = "Total produksi jagung di daerah ini mencapai 8,500 ton pada tahun 2024, menunjukan peningkatan sebesar 5% dibandingkan tahun sebelumnya.",
//                            style = TextStyle(
//                                fontSize = 13.sp,
//                                color = Color.White
//                            )
//                        )
//                        Spacer(modifier = Modifier.height(15.dp))
//                        Text(
//                            text = "Produksi Jagung",
//                            style = TextStyle(
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                color = Color.White
//                            )
//                        )
//                        Text(
//                            text = "Total produksi jagung di daerah ini mencapai 8,500 ton pada tahun 2024, menunjukan peningkatan sebesar 5% dibandingkan tahun sebelumnya.",
//                            style = TextStyle(
//                                fontSize = 13.sp,
//                                color = Color.White
//                            )
//                        )
//                    }
//                }
//            }
//
//        }
//    }
//}



//@Preview(showBackground = true)
//@Composable
//fun BottomSheetStatisticPreview() {
//    bottomSheetStatistic(
//        modifier = Modifier,
//        showBottomSheetStatistic = true, // Ensure it's visible in the preview
//        onShowBottomSheetChange = {}
//    )
//}

