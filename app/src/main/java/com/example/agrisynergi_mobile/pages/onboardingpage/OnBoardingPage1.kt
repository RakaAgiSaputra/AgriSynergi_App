package com.example.agrisynergi_mobile.pages.onboardingpage

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agrisynergi_mobile.R
import com.example.agrisynergi_mobile.data.OnBoardingItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceType")
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen1(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val onBoardings = listOf(
        OnBoardingItem(
            resId = R.drawable.iconagrisynergy,
            title = "Selamat Datang di AgriSynergy!",
            description = " 'Solusi cerdas untuk petani jagung' "
        )
    )

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF5B8C51))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                HorizontalPager(
                    state = pagerState,
                    count = onBoardings.size,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    val item = onBoardings[page]
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = item.resId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(300.dp)
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            color = Color.Yellow,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "AgriSynergy hadir untuk membantu Anda, para petani jagung, dalam meningkatkan produktivitas dan akses pasar secara lebih efektif. Temukan kemudahan dalam mengelola lahan, menjual hasil panen, dan mendapatkan panduan ahli—semua dalam satu aplikasi.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            navController.navigate("onboarding2")
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF13382C))
                    ) {
                        Text("Lanjut", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            if (pagerState.currentPage == onBoardings.size - 1) {
                                navController.navigate("beranda")
                            } else {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D6C33))
                    ) {
                        Text(if (pagerState.currentPage == onBoardings.size - 1) "Lewati" else "Berikutnya", color = Color.White)
                    }

                }
            }
        }
    }
}

