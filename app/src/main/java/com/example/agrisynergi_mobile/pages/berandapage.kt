package com.example.agrisynergi_mobile.pages

import android.widget.CalendarView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CardColors
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import coil.compose.rememberImagePainter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.Agenda
import com.example.agrisynergi_mobile.data.local.sqlite.DatabaseHelper
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.agrisynergi_mobile.database.DatabaseKalender.Kalender
import com.example.agrisynergi_mobile.database.DatabaseKalender.KalenderViewModel
import com.example.agrisynergi_mobile.database.testDatabase.Api
import com.example.agrisynergi_mobile.database.testDatabase.Produk
import com.example.agrisynergi_mobile.database.testDatabase.RetrofitClient1
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.MarketViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController, contentPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
//            .padding(16.dp)
    ) {
        ContentScreen(
            modifier = Modifier.fillMaxSize(),
            navController = navHostController,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    api: Api = RetrofitClient1().instance
) {
    val viewModel = remember { KalenderViewModel(api) }

    val kalender by viewModel.kalender.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCalendars()
    }

    val webinarList = listOf(
        R.drawable.webinar1,
        R.drawable.webinar2,
        R.drawable.webinar3,
        R.drawable.webinar4,
    )
    val pagerState = rememberPagerState()

    val databaseHelper = DatabaseHelper(LocalContext.current)
    var agendaList by remember { mutableStateOf(databaseHelper.readAgendaItems()) }

    // State untuk menyimpan agenda yang sedang dipilih
    var selectedKalender by remember { mutableStateOf<Kalender?>(null) }

    // State untuk menampilkan dialog edit
    var showEditDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            HorizontalPager(
                count = webinarList.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) { page ->
                Box(
                    modifier = Modifier
                        .width(350.dp)
                        .padding(8.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = webinarList[page]),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(vertical = 2.dp),
                        contentScale = ContentScale.Crop
                    )

                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    modifier = Modifier
                        .width(330.dp)
                        .height(320.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    factory = { context ->
                        CalendarView(context)
                    }
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(334.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("agenda") },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5B8C51),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Tambah Agenda", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "icon plus",
                            modifier = Modifier.size(12.dp),
                            tint = Color.White
                        )
                    }
                    when {
                        isLoading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        error != null -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = error ?: "Terjadi kesalahan",
                                    color = Color.Red,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                        kalender.isEmpty() -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Tidak ada agenda saat ini",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                        else -> {
                            KalenderList(
                                items = kalender,
                                onItemClick = { kalender ->
                                    selectedKalender = kalender // Simpan agenda yang dipilih
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    if (selectedKalender != null) {
        KalenderDetailDialog(
            kalender = selectedKalender!!,
            onDismiss = { selectedKalender = null },
            onDelete = { agendaId ->
                // Hapus agenda dari database
                val deletedRows = databaseHelper.deleteDataAgenda(agendaId)
                if (deletedRows > 0) {
                    // Perbarui daftar agenda setelah penghapusan
                    agendaList = databaseHelper.readAgendaItems()
                }
            },
            onEdit = { kalender->
                navController.navigate("agenda/${kalender.id_kalender}")
            }
        )
    }
}

@Composable
fun KalenderList(
    items: List<Kalender>,
    onItemClick: (Kalender) -> Unit
) {
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp, max = 400.dp)
    ) {
        items(items) { kalender ->
            KalenderItem(
                kalender,
                onClick = { onItemClick(kalender) }
            )
        }
    }
}

@Composable
fun KalenderItem(
    kalender: Kalender,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick),

        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = "http://36.74.38.214:8080/api/fileKalender/${kalender.gambar}",
                    error = painterResource(id = R.drawable.imagenotavail)
                ),
                contentDescription = "Gambar Kalender",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f) // Memberikan ruang fleksibel agar teks mengisi sisa ruang
            ) {
                Text(
                    text = kalender.jenis,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = kalender.judul,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text =kalender.tanggal,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = kalender.deskripsi, lineHeight = 10.sp,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3, // Batasi deskripsi hanya 3 baris
                    overflow = TextOverflow.Ellipsis, // Tambahkan "..." jika teks terlalu panjang
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifikasi",
                tint = Color(0xFFF0DC04),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun KalenderDetailDialog(
    kalender: Kalender,
    onDismiss: () -> Unit,
    onDelete: (Int) -> Unit,
    onEdit: (Kalender) -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = kalender.judul,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "http://36.74.38.214:8080/api/fileKalender/${kalender.gambar}",
                        error = painterResource(id = R.drawable.imagenotavail)

                    ),
                    contentDescription = "Image Kalender",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = kalender.tanggal,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = kalender.deskripsi,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = {
                            onEdit(kalender)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5B8C51),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(6.dp),
                    ) {
                        Text("Edit", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(
                        onClick = {
                            onDelete(kalender.id_kalender)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("Hapus", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}