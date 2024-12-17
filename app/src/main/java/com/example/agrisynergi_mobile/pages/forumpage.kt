package com.example.agrisynergymobile.pages

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.database.ModelKomunitas.CommunityData
import com.example.agrisynergi_mobile.database.ModelKomunitas.ForumViewModel
import com.example.agrisynergi_mobile.database.ModelKomunitas.Komentator
import com.example.agrisynergi_mobile.navigation.Screen
import com.example.agrisynergi_mobile.database.ModelKomunitas.Result
import com.example.agrisynergi_mobile.database.ModelKomunitas.UserViewModel
import com.example.agrisynergi_mobile.retrofit.model.User
import com.example.agrisynergi_mobile.retrofit.model.UserData
import kotlinx.coroutines.launch

//Main Forum
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForumScreen(
    navController: NavHostController,
    viewModel: ForumViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val communityData by viewModel.communityData.collectAsState()
    val users by viewModel.users.collectAsState()
    val comments by viewModel.komentarList.collectAsState()

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val coroutineScope = rememberCoroutineScope()
    var selectedCommunity by remember { mutableStateOf<CommunityData?>(null) }

    LaunchedEffect(selectedCommunity?.idKomunitas) {
        selectedCommunity?.idKomunitas?.let { idKomunitas ->
            viewModel.fetchKomentarByKomunitasId(idKomunitas) // ambil commnt berdasarka idkomunitas
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCommunityData()
        viewModel.fetchUsers()
    }

    BottomSheetScaffold(
        sheetContent = {
            selectedCommunity?.let { community ->
                if (comments.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    // Display comments with user data
                    CommentBottomSheetContent(komentarList = comments, users = users, isLiked = false, isDisLiked = false)
                }
            }
        },
        sheetPeekHeight = 0.dp,
        scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopBarForum(
                    onBackClick = { navController.navigateUp() },
                    onSearchClick = { query -> /* Handle search query */ },
                    navController = navController,
                    onAddClick = { navController.navigate(Screen.AddPost.route) }
                )

                when (val result = communityData) {
                    is Result.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    is Result.Success -> {
                        val communityResponse = result.data
                        val communityList = communityResponse?.data ?: emptyList()
                        ForumList(
                            communityData = communityList,
                            users = users,
                            onCommentClick = { community ->
                                selectedCommunity = community
                                coroutineScope.launch {
                                    bottomSheetState.expand()
                                }
                            }
                        )
                    }
                    is Result.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Error: ${result.message}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Red,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(onClick = { viewModel.fetchCommunityData() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

//Setting list forum
@Composable
fun ForumList(
    communityData: List<CommunityData>,
    users: List<User>,
    onCommentClick: (CommunityData) -> Unit) {
    LazyColumn {
        items(communityData) { community ->
            ForumItem(
                community = community,
                users = users,
                onCommentClick = {onCommentClick(community)})
        }
    }
}

//Setting Forum Box
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForumItem(
    community: CommunityData,
    users: List<User>,
    onCommentClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val text = community.deskripsi?.split("No desciption available") ?: listOf("No description available")
    val user = users.find { it.id_user == community.idUser }
    var isBookmarked by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    var isDisLiked by remember { mutableStateOf(false) }
    var modifiedDescription by remember { mutableStateOf(community.deskripsi ?: "No description available") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(Color.White)) {
            Column(modifier = Modifier.fillMaxWidth().shadow(1.dp)) {
                Row(modifier = Modifier.padding(8.dp)) {
                    if (user != null) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = user.foto?.let { "http://36.74.38.214:8080/api/fileUsers/$it" },
                                error = painterResource(id = R.drawable.imagenotavail)
                            ),
                            contentDescription = "Profile Pic",
                            modifier = Modifier
                                .size(55.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        user?.let {
                            Text(
                                text = it.nama,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                text = it.role,
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            )
                        }
                        Text(
                            text = "21 Sep",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(18.dp))

                        if (isExpanded) {
                            Text(
                                text = community.deskripsi ?: "No description available",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            Text(
                                text = "Read less",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier.clickable { isExpanded = false }
                            )
                        } else {
                            Text(
                                text = text.take(30).joinToString(" ") + "...",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            Text(
                                text = "Read more",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier.clickable { isExpanded = true }
                            )
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }

                // Community image
                val communityImageUrl = community.gambar?.takeIf { it.isNotEmpty() }?.let {
                    "http://36.74.38.214:8080/api/fileKomunitas/$it"
                } ?: "default_image_url_here"

                Image(
                    painter = rememberAsyncImagePainter(
                        model = communityImageUrl,
                        error = painterResource(id = R.drawable.imagenotavail)
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .aspectRatio(16 / 9f),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp)
                ) {
                    // Like Button
                    IconButton(onClick = {
                        isLiked = !isLiked
                        if (isLiked) {
                            isDisLiked = false
                            if (community.deskripsi.isNullOrEmpty()) {
                                modifiedDescription = "Meyukai postingan anda"
                            }
                        } else {
                            modifiedDescription = community.deskripsi ?: "No description available"
                        }
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                                contentDescription = "Like",
                                tint = if (isLiked) Color.Red else Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "40",
                                color = if (isLiked) Color.Red else Color.Black,
                                fontSize = 12.sp
                            )
                        }
                    }
                    //Dislike Button
                        IconButton(onClick = {
                            isDisLiked = !isDisLiked
                            if (isDisLiked) {
                                isLiked = false
                                if (community.deskripsi.isNullOrEmpty()) {
                                    modifiedDescription = "Tidak menyukai postingan anda"
                                }
                            } else {
                                modifiedDescription = community.deskripsi ?: "No description available"
                            }
                        }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isDisLiked) Icons.Filled.ThumbDown else Icons.Outlined.ThumbDown,
                                    contentDescription = "DisLike",
                                    tint = if (isDisLiked) Color.Blue else Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "40",
                                    color = if (isDisLiked) Color.Blue else Color.Black,
                                    fontSize = 12.sp
                                )
                            }
                        }


                    IconButton(onClick = onCommentClick) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Comment,
                                contentDescription = "Comment",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "45",
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }

                    IconButton(onClick = {
                        isBookmarked = !isBookmarked
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.Bookmarks,
                                contentDescription = "Bookmark",
                                tint = if (isBookmarked) Color(0xFF13382C) else Color.Black,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "400",
                                color = if (isBookmarked) Color(0xFF13382C) else Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }

                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommentBottomSheetContent(
    komentarList: List<Komentator>,
    users: List<User>,
    isLiked: Boolean,
    isDisLiked: Boolean
) {
    var komentarBaru by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth() // Memastikan Box memenuhi lebar parent
            .padding(vertical = 8.dp) // Menambahkan padding atas dan bawah
    ) {
        Text(
            text = "Komentar",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center) // Membuat teks berada di tengah
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.ime)
    ) {
        // LazyColumn untuk daftar komentar
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            if (komentarList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Komentar tidak tersedia",
                            style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(komentarList) { komentar ->
                    val user = users.find { it.id_user == komentar.id_user }

                    val deskripsiTeks = when {
                        isLiked && komentar.deskripsi.isNullOrEmpty() -> "Menyukai postingan anda"
                        isDisLiked && komentar.deskripsi.isNullOrEmpty() -> "Tidak menyukai postingan anda"
                        else -> komentar.deskripsi ?: "Komentar tidak tersedia"
                    }

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Foto profil user
                            if (user?.foto != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = "http://36.74.38.214:8080/api/fileUsers/${user.foto}",
                                        error = painterResource(id = R.drawable.imagenotavail)
                                    ),
                                    contentDescription = "User Photo",
                                    modifier = Modifier
                                        .size(55.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.imagenotavail),
                                    contentDescription = "Default User Photo",
                                    modifier = Modifier
                                        .size(55.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.width(15.dp))

                            // Nama dan deskripsi komentar
                            Column {
                                Text(
                                    text = user?.nama ?: "Anonymous",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                                Text(
                                    text = deskripsiTeks,
                                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                                )
                            }
                        }
                    }
                }
            }
        }

        // OutlinedTextField untuk user input
        OutlinedTextField(
            value = komentarBaru,
            onValueChange = { komentarBaru = it },
            placeholder = { Text("Tambahkan komentar...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .windowInsetsPadding(WindowInsets.ime),
            shape = RoundedCornerShape(25.dp),
            trailingIcon = {
                IconButton(onClick = {
                    // Tambahkan logika untuk mengirim komentar di sini
                    println("Komentar dikirim: $komentarBaru")
                }) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send Comment",
                        tint = Color(0xFF13382C)
                    )
                }
            },
            singleLine = true
        )
    }
}



//
//@Composable
//fun CommentBottomSheetContent(komentarList: List<Komentator>, users: List<User>) {
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        items(komentarList) { komentar ->
//            val user = users.find { it.id_user == komentar.id_user } // Cari user berdasarkan id_user
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                elevation = 4.dp
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    // Tampilkan foto profil jika ada
//                    if (user?.foto != null) {
//                        Image(
//                            painter = rememberAsyncImagePainter(
//                                model = "http://36.74.38.214:8080/api/fileUsers/${user.foto}",
//                                error = painterResource(id = R.drawable.imagenotavail)
//                            ),
//                            contentDescription = "User Photo",
//                            modifier = Modifier
//                                .size(55.dp)
//                                .clip(CircleShape),
//                            contentScale = ContentScale.Crop
//                        )
//                    } else {
//                        Image(
//                            painter = painterResource(id = R.drawable.imagenotavail),
//                            contentDescription = "Default User Photo",
//                            modifier = Modifier
//                                .size(55.dp)
//                                .clip(CircleShape),
//                            contentScale = ContentScale.Crop
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(15.dp))
//
//                    // Tampilkan nama dan deskripsi komentar
//                    Column {
//                        Text(
//                            text = user?.nama ?: "Anonymous", // Gunakan nama dari user atau default "Anonymous"
//                            style = TextStyle(
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.Black
//                            )
//                        )
//                        Text(
//                            text = komentar.deskripsi ?: "Komentar tidak tersedia", // Null safety untuk deskripsi
//                            style = TextStyle(fontSize = 14.sp, color = Color.Gray)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}


//
//@Composable
//fun CommentItem(komentator: Komentator) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // Avatar pengguna
//        Image(
//            painter = painterResource(id = R.drawable.ellipse__2_), // Placeholder untuk foto pengguna
//            contentDescription = "User Photo",
//            modifier = Modifier
//                .size(55.dp)
//                .clip(CircleShape),
//            contentScale = ContentScale.Crop
//        )
//        Spacer(modifier = Modifier.width(15.dp))
//
//        // Detail komentar
//        Column {
//            Text(
//                text = user.nama_user,
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                )
//            )
//            Text(
//                text = komentator.deskripsi ?: "Komentar tidak tersedia", // Null safety untuk deskripsi
//                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
//            )
//        }
//    }
//}



//Setting Top Bar
@Composable
fun TopBarForum(
    onBackClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    onAddClick: () -> Unit,
    navController: NavHostController
) {
    Column {
        // Top Bar Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF13382C)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.iconback),
                    contentDescription = "Back",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }

            IconButton(onClick = { navController.navigate(Screen.Komunitas.route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.iconcommunity),
                    contentDescription = "Community",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }

        // Search Bar
        SearchBarForum(onSearchClick = onSearchClick, navController = navController)

        // Tags Section (optional)
        TagLineForum()
    }
}

@Composable
fun SearchBarForum(onSearchClick: (String) -> Unit, navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    text = "Search...",
                    style = TextStyle(color = Color.Gray)
                )
            },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp)),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            },
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(onClick = {
            navController.navigate(Screen.AddPost.route)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.iconaddfor),
                contentDescription = "Add",
                tint = Color.Unspecified,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}





@Composable
fun TagLineForum(){
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        val categories = listOf("#Trending", "#Terbaru", "#Hamajagung", "#Musim", "#BibitJagung", "#Hama", "Panen")
        items(categories) { category ->
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = category, color = Color(0xFF5B8C51), fontSize = 10.sp)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun MainScreenPreview() {
    ForumScreen(navController = rememberNavController())
}




