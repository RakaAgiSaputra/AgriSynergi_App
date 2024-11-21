package com.example.agrisynergi_mobile.pages

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.navigation.Screen
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

//MapsScreen Utama
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(navController: NavHostController) {
    var mapView: MapView? by remember { mutableStateOf(null) }
    var showBottomSheetStatistic by remember { mutableStateOf(false) }
    var showBottomSheetMarking by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf<GeoPoint?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Lapisan peta
        maps(
            modifier = Modifier.fillMaxSize().zIndex(0f),
            onMapReady = { mapViewInstance ->
                mapView = mapViewInstance
                mapViewInstance?.let { markLocation(it) { location ->
                    selectedLocation = location
                    showBottomSheetMarking = true
                }}
            }
        )

        // Lapisan bar atas
        mapsTopBar(
            onBackClick = { navController.navigateUp() },
            onSearchClick = { query -> /* Handle query if needed */ },
            mapView = mapView, // Pass mapView
            modifier = Modifier.zIndex(1f)
        )


        // Tombol Zoom
        ZoomControls(
            onZoomIn = { mapView?.controller?.zoomIn() },
            onZoomOut = { mapView?.controller?.zoomOut() },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(16.dp)
                .zIndex(1f)
        )

        // Lapisan bar bawah
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(1f)
        ) {
            mapsBottomBar(
                navController = navController,
                showBottomSheet = showBottomSheetStatistic,
                onShowBottomSheetChange = { showBottomSheetStatistic = it }
            )
            if (showBottomSheetStatistic) {
                bottomSheetStatistic (
                    showBottomSheetStatistic = showBottomSheetStatistic,
                    onShowBottomSheetChange = { showBottomSheetStatistic = it }
                )
            }

            if (showBottomSheetMarking) {
                BottomSheetMarking(
                    showBottomSheetMarking = showBottomSheetMarking,
                    onShowBottomSheetChange = { showBottomSheetMarking = it },
                    selectedLocation = selectedLocation
                )
            }
        }
    }
}

//Pengaturan Maps
@Composable
fun maps(
    modifier: Modifier = Modifier,
    onMapReady: (MapView) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))

            MapView(context).apply {
                val darkMatterTileSource = object : OnlineTileSourceBase(
                    "CartoDB_DarkMatter",
                    1, 19, 256, ".png", arrayOf("https://cartodb-basemaps-a.global.ssl.fastly.net/dark_all/")
                ) {
                    override fun getTileURLString(pMapTileIndex: Long): String {
                        val zoom = MapTileIndex.getZoom(pMapTileIndex)
                        val x = MapTileIndex.getX(pMapTileIndex)
                        val y = MapTileIndex.getY(pMapTileIndex)
                        return "$baseUrl$zoom/$x/$y$mImageFilenameEnding"
                    }
                }
                // agar maps berwarna hitam
                this.tileProvider.tileSource = darkMatterTileSource

                controller.setZoom(15.0)
                controller.setCenter(GeoPoint(-7.2906, 112.7277))

                setBuiltInZoomControls(false)
                setMultiTouchControls(true)

                onMapReady(this)
            }
        },
        modifier = modifier.fillMaxSize()
    )
}


//Pengaturan marking lokasi petani jagung
fun markLocation(mapView: MapView, onMarkerClick: (GeoPoint) -> Unit) {
    val locations = listOf(
        GeoPoint(-7.2906, 112.7277), // Surabaya, East Java
        GeoPoint(-8.152, 112.6397), // Malang
        GeoPoint(-7.8167, 112.0000), // Kediri
        GeoPoint(-8.0983, 112.1767), // Blitar
        GeoPoint(-7.6542, 112.6733), // Pasuruan
        GeoPoint(-7.7517, 113.2233), // Probolinggo
        GeoPoint(-8.2042, 113.7033), // Jember
        GeoPoint(-8.5142, 114.3667), // Banyuwangi
        GeoPoint(-6.8917, 112.4533), // Tuban
        GeoPoint(-7.1517, 111.8917), // Bojonegoro
    )

    val originalDrawable = ContextCompat.getDrawable(mapView.context, R.drawable.iconlocjagung) as BitmapDrawable

    // Pengaturan Membuat dan menyimpan marker
    val markers = locations.map { location ->
        Marker(mapView).apply {
            position = location
            icon = BitmapDrawable(
                mapView.context.resources,
                Bitmap.createScaledBitmap(originalDrawable.bitmap, 100, 100, true) // Ukuran awal ikon
            )
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapView.overlays.add(this)

            setOnMarkerClickListener { _, _ ->
                onMarkerClick(location)
                true
            }
        }
    }

    mapView.addMapListener(object : MapListener {
        override fun onScroll(event: ScrollEvent?): Boolean {
            return false
        }

        override fun onZoom(event: ZoomEvent): Boolean {
            val zoomLevel = event.zoomLevel

            // Pengaturan level zoom maps
            val iconSize = when {
                zoomLevel >= 15 -> 150
                zoomLevel >= 13 -> 120
                else -> 80
            }

            // Pengaturan ukuran ikon marker
            markers.forEach { marker ->
                marker.icon = BitmapDrawable(
                    mapView.context.resources,
                    Bitmap.createScaledBitmap(originalDrawable.bitmap, iconSize, iconSize, true)
                )
            }

            mapView.invalidate()
            return true
        }
    })
}


//Pengaturan bottom shet untuk icon marking lokasi jagung
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetMarking(
    modifier: Modifier = Modifier,
    showBottomSheetMarking: Boolean,
    onShowBottomSheetChange: (Boolean) -> Unit,
    selectedLocation: GeoPoint?
) {
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheetMarking) {
        ModalBottomSheet(
            onDismissRequest = { onShowBottomSheetChange(false) },
            sheetState = sheetState,
            modifier = Modifier
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ){
                Box(
                    modifier = Modifier
                        .shadow(5.dp, shape = RoundedCornerShape(16.dp))
                        .background(Color(0xFF13382C), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(300.dp)
                        .height(300.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(1.dp)
                    ) {
                        Row {
                            Box() {
                                Spacer(modifier = Modifier.height(20.dp))
                                Image (
                                    painter = painterResource(id = R.drawable.profilepetani),
                                    contentDescription = "profil Petani",
                                    modifier = Modifier
                                        .size(65.dp)
                                        .clip(CircleShape)
                                        .align(Alignment.Center),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column (
                                modifier = Modifier
                                    .padding(3.dp),
//                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(350.dp)
                                        .height(40.dp)
                                        .background(Color.Transparent)
                                        .padding(2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Alamat",
                                            style = TextStyle(
                                                fontSize = 9.sp,
                                                color = Color.White
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Text(
                                            text = "Jl. Raya Bagusan, RT.5/RW.29, Bagusan, Terusan, Kec. Gedeg, Kabupaten Mojokerjo, Jawa Timur 613451",
                                            style = TextStyle(
                                                fontSize = 9.sp,
                                                color = Color.White
                                            )
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .width(350.dp)
                                        .height(20.dp)
                                        .background(Color.Transparent)
                                        .padding(2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Jam",
                                            style = TextStyle(
                                                fontSize = 9.sp,
                                                color = Color.White
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(25.dp))
                                        Text(
                                            text = "Tutup - Buka Senin Pukul 06.00",
                                            style = TextStyle(
                                                fontSize = 9.sp,
                                                color = Color.White
                                            )
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .width(350.dp)
                                        .height(20.dp)
                                        .background(Color.Transparent)
                                        .padding(2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
//                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Telepon",
                                            style = TextStyle(
                                                fontSize = 9.sp,
                                                color = Color.White
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Text(
                                            text = "0897-1234-2345",
                                            style = TextStyle(
                                                fontSize = 9.sp,
                                                color = Color.White
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Column(
                        ) {
                            Text(
                                text = "Produksi Jagung",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "Total produksi jagung di daerah ini mencapai 8,500 ton pada tahun 2024, menunjukan peningkatan sebesar 5% dibandingkan tahun sebelumnya.",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Produksi Jagung",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "Total produksi jagung di daerah ini mencapai 8,500 ton pada tahun 2024, menunjukan peningkatan sebesar 5% dibandingkan tahun sebelumnya.",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
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
    mapView: MapView?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    TopAppBar(
        backgroundColor = Color.Transparent,
        modifier = modifier.padding(4.dp),
    ) {
        Row(
            modifier = Modifier
                .width(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.iconback),
                    contentDescription = "Back",
                    modifier = Modifier.size(45.dp),
                    tint = Color.White
                )
            }
            TextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    if (query.isNotEmpty()) {
                        val geocoder = android.location.Geocoder(context)
                        try {
                            val addresses = geocoder.getFromLocationName(query, 1)
                            if (!addresses.isNullOrEmpty()) {
                                val address = addresses[0]
                                val newLocation = GeoPoint(address.latitude, address.longitude)
                                mapView?.controller?.apply {
                                    setCenter(newLocation)
                                    setZoom(15.0)
                                }
                                onSearchClick(query)
                            } else {
                                Toast.makeText(context, "Lokasi tidak ditemukan", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .height(50.dp),
                placeholder = {
                    Text(
                        text = "Search",
                        color = Color.White
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF5B8C51),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                },
                singleLine = true
            )
        }
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


//Pengaturan Bottom Sheet Statiska (Maps Page)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomSheetStatistic(
    modifier: Modifier = Modifier,
    showBottomSheetStatistic: Boolean,
    onShowBottomSheetChange: (Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheetStatistic) {
        ModalBottomSheet(
            onDismissRequest = { onShowBottomSheetChange(false) },
            sheetState = sheetState,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .shadow(5.dp, shape = RoundedCornerShape(16.dp))
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(300.dp)
                        .height(200.dp)
                ) {
                    Column {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "FARM",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                        Row (
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.iconrounddiagram),
                                contentDescription = "Diagram Statistik",
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(16.dp),

                                )
                            Column (
                                modifier = Modifier
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center
                            ){
                                Box(
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(35.dp)
                                        .background(Color(0xFFECFFF2), shape = RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "General",
                                            style = TextStyle(
                                                fontSize = 13.sp,
                                                color = Color(0xFF006D1F)
                                            )
                                        )
                                        Text(
                                            text = "$8,800",
                                            style = TextStyle(
                                                fontSize = 13.sp,
                                                color = Color(0xFF006D1F)
                                            )
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .width(130.dp)
                                        .height(35.dp)
                                        .background(Color(0xFFECFFF2), shape = RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Vehicle",
                                            style = TextStyle(
                                                fontSize = 13.sp,
                                                color = Color(0xFF006D1F)
                                            )
                                        )
                                        Text(
                                            text = "$6,800",
                                            style = TextStyle(
                                                fontSize = 13.sp,
                                                color = Color(0xFF006D1F)
                                            )
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(35.dp)
                                        .background(Color(0xFFECFFF2), shape = RoundedCornerShape(8.dp))
                                        .padding(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "AutoMap",
                                            style = TextStyle(
                                                fontSize = 13.sp,
                                                color = Color(0xFF006D1F)
                                            )
                                        )
                                        Text(
                                            text = "$1,800",
                                            style = TextStyle(
                                                fontSize = 13.sp,
                                                color = Color(0xFF006D1F)
                                            )
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Box(
                    modifier = Modifier
                        .shadow(5.dp, shape = RoundedCornerShape(16.dp))
                        .background(Color(0xFF13382C), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(300.dp)
                        .height(200.dp)
                ) {
                    Column (
                    ) {
                        Text(
                            text = "Produksi Jagung",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "Total produksi jagung di daerah ini mencapai 8,500 ton pada tahun 2024, menunjukan peningkatan sebesar 5% dibandingkan tahun sebelumnya.",
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Produksi Jagung",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "Total produksi jagung di daerah ini mencapai 8,500 ton pada tahun 2024, menunjukan peningkatan sebesar 5% dibandingkan tahun sebelumnya.",
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = Color.White
                            )
                        )
                    }
                }
            }

        }
    }
}
