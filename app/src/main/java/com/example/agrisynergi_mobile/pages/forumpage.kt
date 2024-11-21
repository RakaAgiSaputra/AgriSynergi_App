package com.example.agrisynergymobile.pages

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.shadow
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.Forum
import com.example.agrisynergi_mobile.data.dataforum
import com.example.agrisynergi_mobile.navigation.Screen

//Main Forum
@Composable
fun ForumScreen(navController: NavHostController) {
    val context = LocalContext.current
    Surface(color = MaterialTheme.colorScheme.background) {
        Column {
            CustomTopBar(
                navController = navController,
                onBackClick = { navController.navigateUp() },
                onSearchClick = { query ->
                },
                onAddClick = {
                }
            )
            val forums = dataforum.forums
            ForumList(forums = forums)
        }
    }
}

//Setting list forum
@Composable
fun ForumList(forums: List<Forum>) {
    LazyColumn {
        items(forums.size) { index ->
            ForumItem(forum = forums[index])
        }
    }
}

//Setting Forum Box
@Composable
fun ForumItem(forum: Forum) {
    var isExpanded by remember { mutableStateOf(false) }
    val word = forum.text.split(" ")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp)
            ) {
                Row (
                    modifier = Modifier
                        .padding(8.dp)
                ){
                    Image(
                        painter = rememberImagePainter(
                            data = forum.user.profilePic
                        ),
                        contentDescription = "Profile Pic",
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = forum.user.name,
                            style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,

                                )
                        )
                        Text(
                            text = forum.user.username,
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            text = forum.date,
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))
                        if(isExpanded){
                            Text(
                                text = forum.text,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            Text(
                                text = "Baca lebih sedikit",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .clickable { isExpanded = false }
                            )
                        } else {
                            Text(
                                text = word.take(30).joinToString(" ") + "...",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                            Text(
                                text = "Baca selengkapnya",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .clickable { isExpanded = true }
                            )
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }

                if (forum.hasImage) {
                    forum.imageUrl?.let {
                        Spacer(modifier = Modifier.height(3.dp))
                        Image(
                            painter = rememberImagePainter(
                                data = it
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .aspectRatio(16/9f),
                            contentScale = ContentScale.Crop
                        )

                    }
                }
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
fun CustomTopBar(
    navController: NavHostController,
    onBackClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF13382C))
                .padding(horizontal = 4.dp, vertical = 2.dp),
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF13382C))
                .padding(horizontal = 16.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var searchQuery by remember { mutableStateOf("") }
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    onSearchClick(it)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                placeholder = { Text("Search") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF5B8C51),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Tombol tambah
            IconButton(onClick = {
                onAddClick()
                navController.navigate("addPostScreen")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.iconaddfor),
                    contentDescription = "Add",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF13382C))
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
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
}



