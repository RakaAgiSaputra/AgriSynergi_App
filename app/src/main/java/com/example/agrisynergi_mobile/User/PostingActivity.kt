package com.example.agrisynergi_mobile.User

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpOffset

import com.example.agrisynergi_mobile.R

class PostingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostinganScreen(
                onBackClicked = {
                    finish()
                },
                onAddPostClicked = {
                    startActivity(Intent(this, TambahPostinganActivity::class.java))
                }
            )
        }
    }
}


@Composable
fun PostinganScreen(onBackClicked: () -> Unit, onAddPostClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            PostinganHeader(onBackClicked)

            // Post Grid
            PostGrid(modifier = Modifier.weight(1f))
        }

        // Floating Action Button for new posts
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
                .size(72.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(18.dp),
                    ambientColor = Color.Black.copy(alpha = 1f),
                    spotColor = Color.Black.copy(alpha = 1f)
                )
                .clip(RoundedCornerShape(18.dp))
                .clickable { onAddPostClicked() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pencil),
                contentDescription = "New Post",
                modifier = Modifier.size(72.dp)
            )
        }
    }
}


@Composable
fun PostinganHeader(onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF13382C))
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .clickable { onBackClicked() }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Postingan Saya",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun PostGrid(modifier: Modifier = Modifier) {
    val posts = listOf(
        Pair("Tanam Sehat Indonesia", "Updated today"),
        Pair("Agrikultur : Pertanian Jagung", "Updated yesterday"),
        Pair("pupuk untuk pertanian", "Updated 2 days ago"),
        Pair("Cerita Sukses Bertani", "Last month"),
        Pair("Tanam Sehat Indonesia", "Updated today"),
        Pair("Agrikultur : Pertanian Jagung", "Updated yesterday"),
        Pair("pupuk untuk pertanian", "Updated 2 days ago"),
        Pair("Cerita Sukses Bertani", "Last month"),
        Pair("Agrikultur : Pertanian Jagung", "Updated yesterday"),
        Pair("pupuk untuk pertanian", "Updated 2 days ago"),
        Pair("Cerita Sukses Bertani", "Last month"),
    )

    val images = listOf(
        R.drawable.tanam_sehat,
        R.drawable.img_agrikultur,
        R.drawable.img_perawatan,
        R.drawable.img_sukses_bertani,
        R.drawable.tanam_sehat,
        R.drawable.img_agrikultur,
        R.drawable.img_perawatan,
        R.drawable.img_sukses_bertani,
        R.drawable.img_agrikultur,
        R.drawable.img_perawatan,
        R.drawable.img_sukses_bertani,
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier // Pass the modifier to handle size properly
    ) {
        items(posts.size) { index ->
            PostItem(
                title = posts[index].first,
                updatedTime = posts[index].second,
                imageRes = images[index]
            )
        }
    }
}



@Composable
fun PostItem(title: String, updatedTime: String, imageRes: Int) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(110.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )

                // Three-dot icon with conditional background and color
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)

                        .background(
                            color = if (expanded) Color.Black else Color.Transparent, // Background black when expanded
                            shape = RoundedCornerShape(50) // Circular shape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = if (expanded) Color.White else Color.Black,
                        modifier = Modifier
                            .graphicsLayer(rotationZ = 90f)
                    )
                }

                // Dropdown menu positioned to the right of the icon and opening downward
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = (-20).dp, y = (-80).dp), // Position it right next to the icon
                    modifier = Modifier
                        .background(Color.White)
                        .width(140.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Arsip") },
                        onClick = {
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_archive),
                                contentDescription = "Arsip",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Divider(color = Color.LightGray, thickness = 0.5.dp)

                    DropdownMenuItem(
                        text = { Text("Hapus", color = Color.Red) },
                        onClick = {
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = "Hapus",
                                tint = Color.Red,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(text = title, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text(text = updatedTime, fontSize = 14.sp, color = Color.Gray)
        }
    }
}


