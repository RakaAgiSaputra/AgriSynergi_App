package com.example.agrisynergi_mobile.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.database.ModelKomunitas.CommunityResponse
import com.example.agrisynergi_mobile.database.ModelKomunitas.ForumViewModel
import com.example.agrisynergi_mobile.navigation.Screen
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import com.example.agrisynergi_mobile.database.ModelKomunitas.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody


@Composable
fun AddPostScreen(navController: NavController, forumViewModel: ForumViewModel = hiltViewModel()) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }
    val postResult by forumViewModel.postResult.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }


    LaunchedEffect(postResult) {
        when (val result = postResult) {
            is Result.Success -> {
                navController.navigate(Screen.Forum.route) // Navigate to forum screen after successful post
            }
            is Result.Error -> {
                println("Error: ${result.message}")
            }
            is Result.Loading -> {
                println("Loading...")
            }
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // AddPostTopBar
        AddPostTopBar(onBackClicked = { navController.popBackStack() })

        Spacer(modifier = Modifier.height(20.dp))

        // AddPostContent
        AddPostContent(
            imageUri = imageUri,
            onImagePicked = { imagePickerLauncher.launch("image/*") },
            onDescriptionChanged = { description = it },
            description = description
        )

        Spacer(modifier = Modifier.weight(1f))

        // Posting Button
        Button(
            onClick = {
                if (imageUri != null && description.isNotBlank()) {
                    val descriptionRequestBody = description.toRequestBody(MultipartBody.FORM)
                    val imageFile = File(imageUri?.path ?: "")
                    val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                    val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)


                    forumViewModel.postCommunityData(
                        idUser = RequestBody.create(MultipartBody.FORM, "1"),
                        image = imagePart,
                        description = descriptionRequestBody
                    )
                } else {

                    println("Missing image or description.")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5F897B)
            )
        ) {
            Text("Posting", color = Color(0xFFF0F0F0))
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostTopBar(onBackClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text("Tambah Postingan", color = Color.White)
        },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF13382C)
        )
    )
}

@Composable
fun AddPostContent(
    imageUri: Uri?,
    onImagePicked: () -> Unit,
    onDescriptionChanged: (String) -> Unit,
    description: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp)
            .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri == null) {
            Button(
                onClick = onImagePicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F897B)
                )
            ) {
                Text("Pilih Gambar", color = Color(0xFFF0F0F0))
            }
        } else {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Selected Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Button(
                onClick = onImagePicked,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F897B)
                )
            ) {
                Text("Ganti Gambar", color = Color(0xFFF0F0F0))
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChanged,
        label = { Text("Tambah Caption") },
        placeholder = { Text("Tulis disini..") },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        maxLines = Int.MAX_VALUE
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAddPostScreen() {
    // Mock NavController for preview purposes
    val navController = rememberNavController()

    // Call AddPostScreen with the mock navController
    AddPostScreen(navController = navController)
}
