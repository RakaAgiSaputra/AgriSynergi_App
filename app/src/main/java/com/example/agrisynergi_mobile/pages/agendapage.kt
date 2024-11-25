package com.example.agrisynergi_mobile.pages

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource

@Composable
fun AgendaScreen(navController: NavHostController) {
    Column {
        AgendaTopBar(
            onBackClick = { navController.navigateUp() }
        )
        AddAgendaScreen(navController)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaTopBar(
    onBackClick: () -> Unit
) {
    androidx.compose.material.TopAppBar(
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
            Text(
                text = "Agenda",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            IconButton(
                onClick = { /*TODO*/ }
            ) {}
        }
    }
}

@Composable
fun FilePicker() {
    var selectedFileName by remember { mutableStateOf("No file chosen") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val cursor = context.contentResolver.query(it, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >= 0) {
                        selectedFileName = it.getString(nameIndex) ?: "Unknown"
                    }
                }
            }
            selectedFileUri = uri
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        // File Picker Button
        OutlinedButton(
            onClick = { launcher.launch("image/*") }, // Opens picker for images
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEFEFEF)
            )
        ) {
            Text("Choose File")
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Display selected file name
        Text(text = selectedFileName, fontSize = 14.sp)
    }
}

@Composable
fun DatePickerInput(){
    var tanggal by remember { mutableStateOf("") }
    val context = LocalContext.current

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Format hasil tanggal menjadi "DD/MM/YYYY"
            tanggal = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        },
        java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),
        java.util.Calendar.getInstance().get(java.util.Calendar.MONTH),
        java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Tanggal", fontSize = 14.sp, color = Color.Black)

        OutlinedButton(
            shape = RoundedCornerShape(4.dp),
            onClick = { datePickerDialog.show() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(if (tanggal.isEmpty()) "DD/MM/YYYY" else tanggal)
                Icon(
                    painter = painterResource(id = R.drawable.iconcalendartoday), // Ganti dengan resource ikon Anda
                    contentDescription = "Calendar Icon",
                    modifier = Modifier.size(20.dp), // Ukuran ikon
                    tint = Color.Gray // Warna ikon
                )
            }
        }
    }
}

@Composable
fun AddAgendaScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
            .padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Tambah Agenda",
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Gambar", fontSize = 14.sp, color = Color.Black)

                FilePicker()

                Spacer(modifier = Modifier.height(8.dp))

//                Text(text = "Gambar", fontSize = 14.sp, color = Color.Black)
//                OutlinedButton(
//                    onClick = { /* Pilih gambar */ },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Choose File")
//                }

                var judul by remember { mutableStateOf("") }

                Text(text = "Judul", fontSize = 14.sp, color = Color.Black)
                OutlinedTextField(
                    value = judul,
                    onValueChange = { newValue -> judul = newValue},
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                DatePickerInput()

//                Text(text = "Tanggal", fontSize = 14.sp, color = Color.Black)
//                OutlinedTextField(
//                    value = tanggal,
//                    onValueChange = { newValue -> tanggal = newValue },
//                    modifier = Modifier.fillMaxWidth(),
//                    placeholder = { Text("DD/MM/YY") },
//                    trailingIcon = {
//                        Icon(
//                            painter = painterResource(id = R.drawable.iconcalendartoday),
//                            contentDescription = "Pilih Tanggal"
//                        )
//                    }
//                )

                Spacer(modifier = Modifier.height(16.dp))

                var deskripsi by remember { mutableStateOf("") }

                Text(text = "Deskripsi", fontSize = 14.sp, color = Color.Black)
                OutlinedTextField(
                    value = deskripsi,
                    onValueChange = { newValue -> deskripsi = newValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Jenis", fontSize = 14.sp, color = Color.Black)
                var expanded by remember { mutableStateOf(false) }
                var selectedOption by remember { mutableStateOf("Select") }

                Box(modifier = Modifier.width(112.dp)) {
                    OutlinedButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(selectedOption)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Jenis 1") },
                            onClick = {
                                selectedOption = "Jenis 1"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Jenis 2") },
                            onClick = {
                                selectedOption = "Jenis 2"
                                expanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.width(100.dp))

                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Batal")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { /* Tambahkan agenda */ },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5B8C51)
                        )
                    ) {
                        Text("Tambah", color = Color.White)
                    }
                }
            }
        }
    }
}