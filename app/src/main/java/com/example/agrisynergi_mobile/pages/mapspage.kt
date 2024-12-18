package com.example.agrisynergi_mobile.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.database.DatabaseMaps.Sawah
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.Marker
import coil.compose.rememberAsyncImagePainter
import com.example.agrisynergi_mobile.database.DatabaseMaps.SawahViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import kotlinx.coroutines.launch


//MapsScreen Utama
@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MapsScreen(viewModel: SawahViewModel = hiltViewModel(), navController: NavHostController) {
//
//    val sawahList by viewModel.sawahList.collectAsState()
//    val selectedSawah by viewModel.selectedSawah.collectAsState()
//
//    var showBottomSheet by remember { mutableStateOf(false) }
//
//    LaunchedEffect(key1 = Unit) {
//        viewModel.fetchSawahData()
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        // Top Bar
//        mapsTopBar(
//            onBackClick = { navController.navigateUp() },
//            onSearchClick = { query -> /* belum ada */ },
//            modifier = Modifier.zIndex(1f)
//        )
//
//        val cameraPositionState = rememberCameraPositionState {
//            position = CameraPosition.fromLatLngZoom(LatLng(-3.31669400, 114.59011100), 10f)
//        }
//
//        GoogleMap(
//            modifier = Modifier.fillMaxSize(),
//            cameraPositionState = cameraPositionState
//        ) {
//            sawahList.forEach { sawah ->
//                val latLng = LatLng(
//                    sawah.latitude.toDoubleOrNull() ?: 0.0,
//                    sawah.longitude.toDoubleOrNull() ?: 0.0
//                )
//                Log.d("MapsScreen", "Adding marker: ${sawah.lokasi}, Lat: ${latLng.latitude}, Lng: ${latLng.longitude}")
//                Marker(
//                    state = MarkerState(position = latLng),
//                    title = sawah.lokasi,
//                    snippet = sawah.deskripsi,
//                    icon = BitmapDescriptorFactory.fromResource(R.drawable.iconlocjagung), // Custom icon
//                    onClick = {
//                        viewModel.getSawahByLokasi(sawah.lokasi)
//                        showBottomSheet = true
//                        true
//                    }
//                )
//            }
//        }
//    }
//
//    // Bottom Sheet
//    if (showBottomSheet && selectedSawah != null) {
//        BottomSheetMarking(
//            showBottomSheetMarking = showBottomSheet,
//            onShowBottomSheetChange = { showBottomSheet = it },
//            selectedSawah = selectedSawah
//        )
//    }
//}
//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(viewModel: SawahViewModel = hiltViewModel(), navController: NavHostController) {

    val sawahList by viewModel.sawahList.collectAsState()
    val selectedSawah by viewModel.selectedSawah.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-3.31669400, 114.59011100), 10f)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchSawahData()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val coroutineScope = rememberCoroutineScope()
        mapsTopBar(
            onBackClick = { navController.navigateUp() },
            onSearchClick = { query ->
                searchQuery = query
                val foundSawah = sawahList.find { it.lokasi.equals(query, ignoreCase = true) }
                if (foundSawah != null) {
                    val latLng = LatLng(
                        foundSawah.latitude.toDoubleOrNull() ?: 0.0,
                        foundSawah.longitude.toDoubleOrNull() ?: 0.0
                    )
                    coroutineScope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
                    }
                    Log.d("MapsScreen", "Navigating to: ${foundSawah.lokasi}, Lat: ${latLng.latitude}, Lng: ${latLng.longitude}")
                } else {
                    Log.d("MapsScreen", "Location not found for query: $query")
                }
            },
            modifier = Modifier.zIndex(1f)
        )

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            sawahList.forEach { sawah ->
                val latLng = LatLng(
                    sawah.latitude.toDoubleOrNull() ?: 0.0,
                    sawah.longitude.toDoubleOrNull() ?: 0.0
                )
                Marker(
                    state = MarkerState(position = latLng),
                    title = sawah.lokasi,
                    snippet = sawah.deskripsi,
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.iconlocjagung), // Custom icon
                    onClick = {
                        viewModel.getSawahByLokasi(sawah.lokasi)
                        showBottomSheet = true
                        true
                    }
                )
            }
        }
    }

    if (showBottomSheet && selectedSawah != null) {
        BottomSheetMarking(
            showBottomSheetMarking = showBottomSheet,
            onShowBottomSheetChange = { showBottomSheet = it },
            selectedSawah = selectedSawah
        )
    }
}



@Composable
fun MyMapScreen( ) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        onMapLoaded = {
// Peta

        }
    )
}

@Composable
fun MyMapWithMarker(sawahData: List<Sawah>) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-7.250445, 112.768845), 10f) // Default to Surabaya
    }

    var showBottomSheetMarking by remember { mutableStateOf(false) }
    var selectedSawah by remember { mutableStateOf<Sawah?>(null) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        sawahData.forEach { sawah ->
            val latLng = LatLng(
                sawah.latitude.toDoubleOrNull() ?: 0.0,
                sawah.longitude.toDoubleOrNull() ?: 0.0
            )

            Marker(
                state = MarkerState(position = latLng),
                title = sawah.lokasi,
                snippet = sawah.deskripsi,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.iconlocjagung), // Custom icon
                onClick = {
                    selectedSawah = sawah
                    showBottomSheetMarking = true
                    true
                }
            )
        }
    }

    if (showBottomSheetMarking && selectedSawah != null) {
        BottomSheetMarking(
            showBottomSheetMarking = showBottomSheetMarking,
            onShowBottomSheetChange = { showBottomSheetMarking = it },
            selectedSawah = selectedSawah
        )
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
//@Composable
//fun mapsTopBar(
//    onBackClick: () -> Unit,
//    onSearchClick: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    TopAppBar(
//        title = { Text("Maps Screen") },
//        navigationIcon = {
//            IconButton(onClick = onBackClick) {
//                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//            }
//        },
//        actions = {
//            IconButton(onClick = { onSearchClick("Example Search Query") }) {
//                Icon(Icons.Default.Search, contentDescription = "Search")
//            }
//        },
//        backgroundColor = Color(0xFF5B8C51),
//        modifier = modifier
//    )
//}
@Composable
fun mapsTopBar(
    onBackClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 25.dp)
            .height(56.dp)

            .background(Color(0xFF5B8C51).copy(alpha = 0f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tombol Back
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        // Kotak Pencarian
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search lokasi...", color = Color.White) },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .height(60.dp)
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF5B8C51),
                unfocusedContainerColor = Color(0xFF5B8C51),
                disabledContainerColor = Color(0xFF5B8C51),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                IconButton(onClick = { onSearchClick(searchText) }) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                }
            }
        )
    }
}

//Pengaturan Bottom Bar Maps
//@Composable
//fun mapsBottomBar(
//    navController: NavHostController,
//    showBottomSheet: Boolean,
//    onShowBottomSheetChange: (Boolean) -> Unit,
//    modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .background(Color.Transparent)
//            .padding(8.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(
//                onClick = { onShowBottomSheetChange(true) },
//                shape = CircleShape,
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF13382C))
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.icondiagram),
//                    contentDescription = "Statistik",
//                    modifier = Modifier.size(20.dp),
//                    tint = Color.White
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = "Statistik", color = Color.White)
//            }
//        }
//    }
//}


//@Composable
//fun BottomSheetMarking(
//    showBottomSheetMarking: Boolean,
//    onShowBottomSheetChange: (Boolean) -> Unit,
//    selectedSawah: Sawah?
//) {
//    // You can use a BottomSheetScaffold or similar approach for this
//    if (showBottomSheetMarking) {
//        // Display the bottom sheet content here
//        Text(
//            text = "Sawah: ${selectedSawah?.lokasi}",
//            modifier = Modifier.fillMaxWidth().padding(16.dp)
//        )
//    }
//}


//Pengaturan bottom shet untuk icon marking lokasi jagung
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetMarking(
    showBottomSheetMarking: Boolean,
    onShowBottomSheetChange: (Boolean) -> Unit,
    selectedSawah: Sawah?
) {
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheetMarking && selectedSawah != null) {
        ModalBottomSheet(
            onDismissRequest = { onShowBottomSheetChange(false) },
            sheetState = sheetState,
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // Jawa Timur Box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color(0xFF5B8C51), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.iconloc),
                                contentDescription = "Location Icon",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(1.dp))
                            Text(
                                text = "Jawa Timur",
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }
                    }

                    // Tonnes of Corn Box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color(0xFF5B8C51), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.iconjagung),
                                contentDescription = "Corn Icon",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "1.300 ton",
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }
                    }

                    // Farmers Box
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color(0xFF5B8C51), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.iconpetani),
                                contentDescription = "Farmers Icon",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "4 pertanian",
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .shadow(5.dp, shape = RoundedCornerShape(16.dp))
                        .background(Color(0xFF5B8C51), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(330.dp)
                        .height(600.dp)
                ){
                    Column(

                    ) {
//                        if (selectedSawah != null) {
//                            Image(
//                                painter = rememberAsyncImagePainter(
//                                    model = "http://36.74.31.200:8080/api/fileSawah/${selectedSawah?.foto_lokasi}",
//                                    error = painterResource(id = R.drawable.imagenotavail)
//                                ),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(180.dp)
//                                    .clip(RoundedCornerShape(10.dp)),
//                                contentScale = ContentScale.Crop
//                            )
//
//                        }


                        Spacer(modifier = Modifier.height(16.dp))

                        //box informasi wilayah
                        Box(
                            modifier = Modifier
                                .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp)) // White border
                                .background(Color.Transparent)
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Column {
                                if (selectedSawah!= null) {
                                    Text(
                                        text = "Lokasi: ${selectedSawah?.lokasi}",
                                        color = Color.White
                                    )
                                }
                                if (selectedSawah != null) {
                                    Text(
                                        text = "Luas: ${selectedSawah?.luas} ha",
                                        color = Color.White
                                    )
                                }
                                if (selectedSawah != null) {
                                    Text(
                                        text = "Jenis Tanah: ${selectedSawah?.jenis_tanah}",
                                        color = Color.White
                                    )
                                }
                                if (selectedSawah != null) {
                                    Text(
                                        text = "Hasil Panen: ${selectedSawah?.hasil_panen}",
                                        color = Color.White
                                    )
                                }
//                                if (selectedSawah != null) {
//                                    Text(
//                                        text = "Deskripsi: ${selectedSawah?.deskripsi}",
//                                        color = Color.White
//                                    )
//                                }
                            }

                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        // Description Box
                        Box(
                            modifier = Modifier
                                .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp))
                                .background(Color.Transparent)
                                .padding(16.dp)
                                .fillMaxWidth()
                        )  {
                            Column {
                                Text(
                                    text = "Deskripsi",
                                    color = Color.White
                                )
                                Text(
                                    text = "${selectedSawah?.deskripsi}",
                                    color = Color.White
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}




@Composable
fun SawahImage(url: String) {
    Image(
        painter = rememberAsyncImagePainter(
            model = url,
            error = painterResource(id = R.drawable.imagenotavail) // Placeholder jika gagal
        ),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}


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



@Preview(showBackground = true)
@Composable
fun BottomSheetStatisticPreview() {
    val sawahViewModel: SawahViewModel = hiltViewModel()
    MapsScreen(viewModel = sawahViewModel, rememberNavController())
}
