package com.example.agrisynergi_mobile

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.navigation.NavigationItem
import com.example.agrisynergi_mobile.navigation.Screen
import com.example.agrisynergi_mobile.pages.DetailMarketScreen
import com.example.agrisynergi_mobile.pages.MainScreen
import com.example.agrisynergi_mobile.pages.MapsScreen
import com.example.agrisynergi_mobile.pages.MarketScreen
import com.example.agrisynergymobile.pages.ForumScreen

@Composable
fun AgrisynergiApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != null && !currentRoute.startsWith("detailmarket") && currentRoute != Screen.Maps.route && currentRoute != Screen.Forum.route) {
                BottomNavigationBar(navController)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Beranda.route,
            modifier = modifier.padding(contentPadding)
        ) {
            composable(Screen.Beranda.route) {
                MainScreen(navHostController = navController, contentPadding = contentPadding)
            }
            composable(Screen.Maps.route) {
                MapsScreen(navController = navController)
            }
            composable(Screen.Market.route) {
                MarketScreen(navController = navController)
            }
            composable("detailmarket/{marketId}") { backStackEntry ->
                val marketId = backStackEntry.arguments?.getString("marketId")?.toIntOrNull()
                if (marketId != null) {
                    DetailMarketScreen(marketId = marketId, navController = navController)
                }
            }
            composable(Screen.Forum.route) {
                ForumScreen(navController = navController)
            }
            composable(Screen.User.route) {

            }

            }
        }
    }


@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = Color(0xFF13382C)
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = "Beranda",
                icon = R.drawable.iconberanda,
                screen = Screen.Beranda
            ),
            NavigationItem(
                title = "Maps",
                icon = R.drawable.iconmaps,
                screen = Screen.Maps
            ),
            NavigationItem(
                title = "Market",
                icon = R.drawable.iconmarket,
                screen = Screen.Market
            ),
            NavigationItem(
                title = "Konsultasi",
                icon = R.drawable.iconconsultan,
                screen = Screen.Konsultasi
            ),
            NavigationItem(
                title = "User",
                icon = R.drawable.iconuser1,
                screen = Screen.User
            )
        )
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgrisynergiPreview() {
    AgrisynergiApp()
}
