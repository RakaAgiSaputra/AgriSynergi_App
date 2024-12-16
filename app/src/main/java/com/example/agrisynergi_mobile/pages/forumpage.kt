package com.example.agrisynergymobile.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Share
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
import com.example.agrisynergi_mobile.navigation.Screen
import com.example.agrisynergi_mobile.database.ModelKomunitas.Result
import com.example.agrisynergi_mobile.database.ModelKomunitas.UserViewModel
import com.example.agrisynergi_mobile.retrofit.model.User
import com.example.agrisynergi_mobile.retrofit.model.UserData

//Main Forum
@Composable
fun ForumScreen(
    navController: NavHostController,
    viewModel: ForumViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val communityData by viewModel.communityData.collectAsState()
    val users by viewModel.users.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCommunityData()
        viewModel.fetchUsers()
    }

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
                    ForumList(communityData = communityList, users = users)
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


//Setting list forum
@Composable
fun ForumList(communityData: List<CommunityData>,  users: List<User>) {
    LazyColumn {
        items(communityData) { community ->
            ForumItem(community = community, users = users)
        }
    }
}

//Setting Forum Box
@Composable
fun ForumItem(community: CommunityData, users: List<User>) {
    var isExpanded by remember { mutableStateOf(false) }
    val text = community.deskripsi?.split(" ") ?: listOf("No description available")

    val user = users.find { it.id_user == community.id_user }

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
                                text = community.deskripsi ?: "No description available",  // Safe check for null
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

                // Safe check for gambar (image)
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
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Like",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                        )
                    }
                    Text(
                        text = "40",
                        color = Color.Black,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Comment,
                            contentDescription = "Comment",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                        )
                    }
                    Text(
                        text = "45",
                        color = Color.Black,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Bookmarks,
                            contentDescription = "Bookmark",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                        )
                    }
                    Text(
                        text = "400",
                        color = Color.Black,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
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



