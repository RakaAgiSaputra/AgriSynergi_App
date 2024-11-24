package com.example.agrisynergi_mobile.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.Forum
import com.example.agrisynergi_mobile.data.dataforum
import com.example.agrisynergymobile.pages.ForumItem


@Composable
fun CommunityMemberScreen(navController: NavHostController) {
    Surface {
        Column {
            TopBarCommunity (
                onBackClick = { navController.navigateUp() }
            )
            val forums = dataforum.forums
            CommunityList(forums = forums)
        }
    }

}

@Composable
fun TopBarCommunity(
    onBackClick: () -> Unit
) {
        TopAppBar(
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
                    text = "Anggota",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(0xFF13382C),
                        fontWeight = FontWeight.Bold
                    )
                )

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(id = R.drawable.iconsearch),
                        contentDescription = "Icon Keranjang",
                        tint = Color.White
                    )
                }
            }
        }
    }

@Composable
fun CommunityList(forums: List<Forum>) {
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Anggota Komunitas"
    )
    Spacer(modifier = Modifier.height(20.dp))
    LazyColumn {
        items(forums.size) { index ->
            ContentCommunity(forum = forums[index])
        }
    }
}

@Composable
fun ContentCommunity(forum: Forum) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
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
                androidx.compose.material.Text(
                    text = forum.user.name,
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,

                        )
                )
                androidx.compose.material.Text(
                    text = forum.user.username,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                )

            }
        }
    }
}

