package com.example.agrisynergi_mobile.consultant

import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agrisynergi_mobile.R
import coil.compose.rememberImagePainter
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController,viewModel: ChatViewModel = viewModel()){
    val userMessage = viewModel.userMessage.value
    val chatViewModel: ChatViewModel = viewModel()
    val userMessages by chatViewModel.userMessage
    var sendMessage by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val textResult = viewModel.textGenerationResult.collectAsState().value

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Launcher untuk memilih gambar dari galeri
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val message = UserMessage(
                user = "user",
                type = "image",
                message = "",
                isUser = true,
                imageUri = it
            )
            chatViewModel.addMessage(message)
        }
    }


//     Tampilkan gambar jika ada
    imageUri?.let {
        Image(painter = rememberImagePainter(it), contentDescription = "Selected Image")
    }

    Scaffold(
        topBar = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_leftleft),
                            contentDescription = null,
    //                        tint = colorResource(R.color.white)
                        )
                    }

                    Text(
                        text = "Consultation",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(500),
                        modifier = Modifier.fillMaxWidth()
                            .padding(end = 50.dp)
                    )
                }
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Black))
            }

        },

        bottomBar = {
            Row (
                modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()).windowInsetsPadding(WindowInsets.ime),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {
                        galleryLauncher.launch("image/*")
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.group_galeri),
                        contentDescription = null
                    )
                }
                OutlinedTextField(
                    value = sendMessage,
                    onValueChange = { sendMessage = it },
                    placeholder = { Text("Type your messege...") },
                    modifier = Modifier.weight(1f).padding(start = 8.dp, end = 8.dp),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (sendMessage.isNotBlank()){
                                    val message = UserMessage("user","gambar",sendMessage,false)
                                    chatViewModel.addMessage(message)
                                    chatViewModel.generateStoryFromApi(sendMessage)
                                    sendMessage = ""

                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = null
                            )
                        }
                    },
                    singleLine = true,
                )
            }
        }
    ) { paddingValues ->

        Box() {
            Image(
                painter = painterResource(R.drawable.agri_synergy_2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            ListChat(Modifier.padding(paddingValues), userMessage)

        }

    }
}

@Composable
fun ListChat(modifier: Modifier = Modifier, userMessage: List<UserMessage>){
    LazyColumn(
        modifier = modifier,
    ){

        itemsIndexed(userMessage) { index, message ->
            if (index % 2 == 0) {
                ConsultChat(message)
            } else {
                PetaniChat(message)
            }
        }
    }
}

@Composable
fun ConsultChat(userMessage: UserMessage) {
    Box(
        modifier = Modifier.width(500.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = userMessage.user,
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight(500)
                )
                if (userMessage.imageUri != null) {
                    Image(
                        painter = rememberImagePainter(userMessage.imageUri),
                        contentDescription = "Image Message",
                        modifier = Modifier
                            .size(150.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                            .padding(4.dp)
                    )
                } else {
                    ChatBubbleConsultant(message = userMessage.message, isUser = false)
                }
            }
        }
    }
}


@Composable
fun PetaniChat(userMessage: UserMessage) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ellipse__2_),
            contentDescription = null
        )
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = userMessage.user,
                modifier = Modifier.padding(start = 8.dp),
                fontWeight = FontWeight(500)
            )
            if (userMessage.imageUri != null) {
                Image(
                    painter = rememberImagePainter(userMessage.imageUri),
                    contentDescription = "Image Message",
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                        .padding(4.dp)
                )
            } else {
                ChatBubbleUser(message = userMessage.message, isUser = false)
            }
        }
    }
}



@Composable
fun ChatBubbleUser(message: String, isUser: Boolean) {
    Row(Modifier.height(IntrinsicSize.Max)) {
        Column(
            modifier = Modifier.background(
                color = colorResource(R.color.ijo_muda),
                shape = ReverseTriangleEdgeShape(30)
            )
                .width(0.dp)
                .fillMaxHeight().padding(8.dp)
        ) {
        }
        Column(
            modifier = Modifier.background(
                color = colorResource(R.color.ijo_muda),
                shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 0.dp)
            ).padding(8.dp)
        ) {
            Text(text = message, color = Color.White)
        }
    }
}

@Composable
fun ChatBubbleConsultant(message: String, isUser: Boolean) {
    Row(Modifier.height(IntrinsicSize.Max)) {
        Column(
            modifier = Modifier.background(
                color = colorResource(R.color.ijo),
                shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 15.dp)
            ).padding(10.dp)
        ) {
            Text(text = message, color = Color.White)
        }
        Column(
            modifier = Modifier.background(
                color = colorResource(R.color.ijo),
                shape = TriangleEdgeShape(30)
            )
                .width(0.dp)
                .fillMaxHeight().padding(8.dp)
        ) {
        }
    }
}