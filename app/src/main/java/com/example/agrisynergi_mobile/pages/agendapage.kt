package com.example.agrisynergi_mobile.pages

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.agrisynergi_mobile.data.local.sqlite.DatabaseHelper
import com.example.agrisynergi_mobile.viewmodel.DetailAgenda
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import java.io.File
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@Composable
fun AgendaScreen(navController: NavHostController, agendaId: Int? = null) {
    Column {
        AgendaTopBar(
            onBackClick = { navController.navigateUp() }
        )
        AddAgendaScreen(navController, agendaId)
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
fun FilePicker(
    initialImagePath: String? = null,
    onFileSelected: (Uri?) -> Unit
) {
    var selectedFileName by remember { mutableStateOf("No file chosen") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    fun getFileName(context: Context, uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex >= 0 && cursor.moveToFirst()) {
                    result = cursor.getString(nameIndex)
                }
            }
        }

        // Jika masih null, coba ambil dari path
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut!! + 1)
            }
        }

        // Jika tetap null, gunakan nama default
        return result ?: "image_${System.currentTimeMillis()}.jpg"
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val fileName = getFileName(context, it)
                selectedFileName = fileName
                selectedFileUri = uri
                onFileSelected(uri)
            } catch (e: Exception) {
                Log.e("FilePicker", "Error selecting file", e)
                Toast.makeText(context, "Gagal memilih file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Initialize with existing image if available
    LaunchedEffect(initialImagePath) {
        initialImagePath?.let {
            selectedFileName = File(it).name
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Show existing image if available
        initialImagePath?.let { path ->
            Image(
                painter = rememberImagePainter(path),
                contentDescription = "Existing Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // File Picker Button
        OutlinedButton(
            onClick = { launcher.launch("image/*") }, // Opens picker for images
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEFEFEF)
            )
        ) {
            Text("Pilih Gambar")
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Display selected file name
        Text(text = selectedFileName, fontSize = 14.sp)
    }
}

@Composable
fun DatePickerInput(
    selectedDate: String,
    onDateSelected: (String) -> Unit // Callback untuk mengembalikan tanggal ke parent
) {
    val context = LocalContext.current

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Format hasil tanggal menjadi "DD/MM/YYYY"
            val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            onDateSelected(formattedDate) // Berikan tanggal ke parent
        },
        java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),
        java.util.Calendar.getInstance().get(java.util.Calendar.MONTH),
        java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Tanggal", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)

        OutlinedButton(
            shape = RoundedCornerShape(4.dp),
            onClick = { datePickerDialog.show() },
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(if (selectedDate.isEmpty()) "DD/MM/YYYY" else selectedDate)
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
fun AddAgendaScreen(navController: NavHostController, agendaId: Int? = null) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val databaseHelper = DatabaseHelper(context)

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var imagePath by remember { mutableStateOf("") }
    var judul by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var deskripsiAgenda by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("Select") }
    var expanded by remember { mutableStateOf(false) }

    // If editing an existing agenda, load its data
    LaunchedEffect(agendaId) {
        agendaId?.let { id ->
            val agenda = databaseHelper.readAgendaItems().find { it.id == id }
            agenda?.let {
                judul = it.judul
                tanggal = it.tanggal
                deskripsiAgenda = it.deskripsiAgenda
                selectedOption = it.jenis
                imagePath = it.imagePath ?: ""
                selectedFileUri = it.imagePath?.let { path -> Uri.parse(path) }
            }
        }
    }

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
                .fillMaxWidth()
                .verticalScroll(scrollState),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = if (agendaId == null) "Tambah Agenda" else "Edit Agenda",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Gambar", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)

                FilePicker(
                    initialImagePath = imagePath,
                    onFileSelected = { uri ->
                        selectedFileUri = uri
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Judul", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = judul,
                    onValueChange = { newValue -> judul = newValue},
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                DatePickerInput(selectedDate = tanggal, // Hubungkan ke variabel tanggal
                    onDateSelected = { newDate -> tanggal = newDate }
                )            // Callback untuk memperbarui tanggal)

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Deskripsi", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = deskripsiAgenda,
                    onValueChange = { newValue -> deskripsiAgenda = newValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Jenis", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)

                Box(modifier = Modifier.width(112.dp)) {
                    OutlinedButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(selectedOption)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Reminder") },
                            onClick = {
                                selectedOption = "Reminder"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Event") },
                            onClick = {
                                selectedOption = "Event"
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
                        Text("Batal", color = Color.Black, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            // Validate input
                            if (judul.isBlank() || tanggal.isBlank() || deskripsiAgenda.isBlank() || selectedOption == "Select") {
                                Toast.makeText(context, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            // Save or update image
                            val newImagePath = selectedFileUri?.let { imageUri ->
                                databaseHelper.saveImageToInternalStorage(context, imageUri)
                            } ?: imagePath

                            // Determine whether to insert or update
                            val result = if (agendaId == null) {
                                // Adding new agenda
                                databaseHelper.insertDataAgenda(
                                    DetailAgenda(
                                        imagePath = newImagePath,
                                        judul = judul,
                                        tanggal = tanggal,
                                        deskripsiAgenda = deskripsiAgenda,
                                        jenis = selectedOption
                                    )
                                )
                            } else {
                                // Updating existing agenda
                                databaseHelper.updateDataAgenda(
                                    DetailAgenda(
                                        id = agendaId,
                                        imagePath = newImagePath,
                                        judul = judul,
                                        tanggal = tanggal,
                                        deskripsiAgenda = deskripsiAgenda,
                                        jenis = selectedOption
                                    )
                                )
                            }

                            if (result != -1L) {
                                Toast.makeText(
                                    context,
                                    if (agendaId == null) "Agenda berhasil ditambahkan!" else "Agenda berhasil diperbarui!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, "Gagal menyimpan agenda", Toast.LENGTH_SHORT).show()
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5B8C51)
                        )
                    ) {
                        Text(if (agendaId == null) "Tambah" else "Perbarui", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}