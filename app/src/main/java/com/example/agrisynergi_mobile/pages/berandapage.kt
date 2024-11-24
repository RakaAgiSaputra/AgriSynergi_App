package com.example.agrisynergi_mobile.pages

import android.widget.CalendarView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.agrisynergi_mobile.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController, contentPadding: PaddingValues) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        content = { innerPadding ->
            ContentScreen(
                modifier = Modifier
                    .padding(
                        start = innerPadding.calculateStartPadding(layoutDirection = androidx.compose.ui.unit.LayoutDirection.Ltr),
                        end = innerPadding.calculateEndPadding(layoutDirection = androidx.compose.ui.unit.LayoutDirection.Ltr),
                        top = innerPadding.calculateTopPadding()
                    )
            )
        }
    )
}



@OptIn(ExperimentalPagerApi::class)
@Composable
fun ContentScreen(modifier: Modifier = Modifier) {
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
                    .height(250.dp)
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
    }
}



