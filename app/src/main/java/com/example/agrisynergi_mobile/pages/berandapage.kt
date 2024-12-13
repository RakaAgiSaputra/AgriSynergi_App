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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CardColors
import androidx.compose.foundation.clickable
import coil.compose.rememberImagePainter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.Agenda
import com.example.agrisynergi_mobile.data.local.sqlite.DatabaseHelper
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController, contentPadding: PaddingValues) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        content = { innerPadding ->
            ContentScreen(
                modifier = Modifier
                    .padding(
                        start = innerPadding.calculateStartPadding(layoutDirection = androidx.compose.ui.unit.LayoutDirection.Ltr),
                        end = innerPadding.calculateEndPadding(layoutDirection = androidx.compose.ui.unit.LayoutDirection.Ltr),
                        top = innerPadding.calculateTopPadding()
                    ),
                navController = navHostController // Oper nilai navHostController ke ContentScreen
            )
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ContentScreen(modifier: Modifier = Modifier, navController: NavHostController) {
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
    var selectedAgenda by remember { mutableStateOf<Agenda?>(null) }

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
                    .height(250.dp)
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
                    AgendaList(
                        agendaList = agendaList,
                        onItemClick = { agenda ->
                            selectedAgenda = agenda // Simpan agenda yang dipilih
                        }
                    )
                }
            }
        }
    }
    if (selectedAgenda != null) {
        AgendaDetailDialog(
            agenda = selectedAgenda!!,
            onDismiss = { selectedAgenda = null },
            onDelete = { agendaId ->
                // Hapus agenda dari database
                val deletedRows = databaseHelper.deleteDataAgenda(agendaId)
                if (deletedRows > 0) {
                    // Perbarui daftar agenda setelah penghapusan
                    agendaList = databaseHelper.readAgendaItems()
                }
            },
            onEdit = { agenda ->
                navController.navigate("agenda/${agenda.id}")
            }
        )
    }
}


@Composable
fun AgendaList(
    agendaList: List<Agenda>,
    onItemClick: (Agenda) -> Unit
) {
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp, max = 400.dp)
    ) {
        items(agendaList) { agenda ->
            AgendaItem(
                agenda = agenda,
                onClick = { onItemClick(agenda) }
            )
        }
    }
}

@Composable
fun AgendaItem(
    agenda: Agenda,
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
            verticalAlignment = Alignment.CenterVertically // Menjaga elemen sejajar secara vertikal
        ) {
            Image(
                painter = rememberImagePainter(agenda.imagePath),
                contentDescription = "Gambar Agenda",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp) // Ukuran gambar
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f) // Memberikan ruang fleksibel agar teks mengisi sisa ruang
            ) {
                Text(
                    text = agenda.jenis,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = agenda.judul,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = agenda.tanggal,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = agenda.deskripsiAgenda,
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
fun AgendaDetailDialog(
    agenda: Agenda,
    onDismiss: () -> Unit,
    onDelete: (Int) -> Unit,
    onEdit: (Agenda) -> Unit
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
                    text = agenda.judul,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberImagePainter(agenda.imagePath),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = agenda.tanggal,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = agenda.deskripsiAgenda,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = {
                            onEdit(agenda)
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
                            onDelete(agenda.id)
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
