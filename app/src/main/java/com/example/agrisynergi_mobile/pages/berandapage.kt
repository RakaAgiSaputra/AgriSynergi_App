package com.example.agrisynergi_mobile.pages

import android.widget.CalendarView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun MainScreen(navHostController: NavHostController, contentPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
//            .padding(16.dp)
    ) {
        ContentScreen(
            modifier = Modifier.fillMaxSize(),
            navController = navHostController
        )
    }
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
                    .height(240.dp)
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
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
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
                        Text(text = "Tambah Agenda", fontSize = 16.sp)

                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "icon plus",
                            modifier = Modifier.size(12.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun MainScreenPreview() {
    MainScreen(navHostController = rememberNavController(), contentPadding = PaddingValues())
}
