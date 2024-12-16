package com.example.agrisynergi_mobile

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.User.UserProfileScreen
import com.example.agrisynergi_mobile.auth.AuthManageer
import com.example.agrisynergi_mobile.consultant.ChatScreen
import com.example.agrisynergi_mobile.navigation.NavigationItem
import com.example.agrisynergi_mobile.navigation.Screen
import com.example.agrisynergi_mobile.pages.CommunityMemberScreen
import com.example.agrisynergi_mobile.pages.BeliScreen
import com.example.agrisynergi_mobile.pages.CheckoutScreen
import com.example.agrisynergi_mobile.pages.DetailMarketScreen
import com.example.agrisynergi_mobile.pages.KeranjangScreen
import com.example.agrisynergi_mobile.pages.MainScreen
import com.example.agrisynergi_mobile.pages.MapsScreen
import com.example.agrisynergi_mobile.pages.MarketScreen
import com.example.agrisynergi_mobile.pages.NotifScreen
import com.example.agrisynergi_mobile.pages.SplashScreen
import com.example.agrisynergi_mobile.pages.login.LoginRegistScreen
import com.example.agrisynergi_mobile.pages.onboardingpage.OnBoardingScreen1
import com.example.agrisynergi_mobile.pages.onboardingpage.OnBoardingScreen2
import com.example.agrisynergi_mobile.pages.onboardingpage.OnBoardingScreen3
import com.example.agrisynergi_mobile.pages.onboardingpage.OnBoardingScreen4
import com.example.agrisynergi_mobile.pages.onboardingpage.OnBoardingScreen5
import com.example.agrisynergi_mobile.retrofit.model.view.LoginScreen
import com.example.agrisynergi_mobile.retrofit.model.view.RegisterScreen
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.LoginViewModel
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.OtpViewModel
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.RegisterViewModel
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.SharedPreferenceManager
import com.example.agrisynergi_mobile.retrofit.testing.OtpScreen
import com.example.agrisynergi_mobile.utils.shouldShowBottomBar
import com.example.agrisynergymobile.pages.ForumScreen
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.math.log

@Composable
fun AgrisynergiApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    auth: FirebaseAuth,
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val registerViewModel = RegisterViewModel()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)
    val sharedPreferenceManager = SharedPreferenceManager(context)
    val loginVewModel = LoginViewModel(sharedPreferenceManager)
    val authManager = AuthManageer(context, auth, navController,sharedPreferenceManager)
    val isLoggedIn = sharedPreferenceManager.getLoginStatus()
    val activity = context as? Activity
    val otpViewModel: OtpViewModel = viewModel()

    val startDestination = if (isLoggedIn) {
        Screen.Beranda.route  // Arahkan ke Beranda jika sudah login
    } else {
        Screen.Splash.route  // Arahkan ke Splash jika belum login
    }

    Scaffold(
        topBar = {
            BerandaTopBar(navController = navController, currentRoute = currentRoute)
        },
        bottomBar = {
            AnimatedVisibility(
                visible = currentRoute.shouldShowBottomBar()
            ) {
                BottomNavigationBar(navController)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(contentPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(navController = navController)
            }
            composable(Screen.OnBoarding1.route) {
                OnBoardingScreen1(navController = navController)
            }
            composable(Screen.OnBoarding2.route) {
                OnBoardingScreen2(navController = navController)
            }
            composable(Screen.OnBoarding3.route) {
                OnBoardingScreen3(navController = navController)
            }
            composable(Screen.OnBoarding4.route) {
                OnBoardingScreen4(navController = navController)
            }
            composable(Screen.OnBoarding5.route) {
                OnBoardingScreen5(navController = navController)
            }
            composable(Screen.LoginRegist.route) {
                LoginRegistScreen(navController)
            }
            composable(Screen.Regist.route) {
                RegisterScreen(
                    navController = navController,
                    registerViewModel = registerViewModel,
                    credentialManager = credentialManager,
                    authManageer = authManager
//                    registerWithGoogle = {
//                        AuthWithGoogle(
//                            scope = scope,
//                            context = context,
//                            credentialManager = credentialManager,
//                            auth = auth,
//                            navController = navController,
//                            isUserAgri = false
//                        )
//                    }
                )
            }
            composable(Screen.Login.route){
                LoginScreen(
                    navController = navController,
                    viewModel = loginVewModel,
                    context = context,
                    coroutineScope = scope,
                    authManageer = authManager,
                    credentialManager = credentialManager,
//                    loginWithGoogle = {
//                        AuthWithGoogle(
//                            scope = scope,
//                            context = context,
//                            credentialManager = credentialManager,
//                            auth = auth,
//                            navController = navController,
//                            isUserAgri = true
//                        )
//                    }
                )
            }

            composable(Screen.Otp.route){
                // Check if activity is null
                if (activity != null) {
                    OtpScreen(viewModel = otpViewModel, activity = activity)
                } else {
                    Toast.makeText(context, "Activity context is not available", Toast.LENGTH_SHORT).show()
                }
            }

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
            composable(Screen.Konsultasi.route) {
                ChatScreen(navController= navController)
            }
            composable(Screen.Notifikasi.route) {
                NotifScreen(navController= navController)
            }
           composable("user_profile") {
                UserProfileScreen(
                    onOptionSelected = { /* Handle navigasi berdasarkan opsi */ },
                    onBackClicked = { navController.popBackStack() },
                    onClickLogout = {
                        logOut(auth, sharedPreferenceManager, navController)
                    }
                )
            }
            composable(Screen.Komunitas.route) {
                CommunityMemberScreen(navController = navController)
            }
            composable(Screen.Komunitas.route) {
                CommunityMemberScreen(navController = navController)
            }

            composable("keranjang/{marketId}") { backStackEntry ->
                val marketId = backStackEntry.arguments?.getString("marketId")?.toIntOrNull()
                if (marketId != null) {
                    KeranjangScreen(marketId = marketId, navController = navController)
                }
            }
            composable("belisekarang/{marketId}") { backStackEntry ->
                val marketId = backStackEntry.arguments?.getString("marketId")?.toIntOrNull()
                if (marketId != null) {
                    BeliScreen(marketId = marketId, navController = navController)
                }
            }
            composable("checkout/{marketId}") { backStackEntry ->
                val marketId = backStackEntry.arguments?.getString("marketId")?.toIntOrNull()
                if (marketId != null) {
                    CheckoutScreen(marketId = marketId, navController = navController)
                }
            }

        }
    }
}

private fun logOut(auth: FirebaseAuth, sharedPreferenceManager: SharedPreferenceManager, navController: NavHostController) {
    auth.signOut()
    sharedPreferenceManager.logout()
    navController.navigate(Screen.Splash.route) {
        popUpTo(Screen.Beranda.route) { inclusive = true }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerandaTopBar(navController: NavController, currentRoute: String?) {
    val context = LocalContext.current

    if (currentRoute == Screen.Beranda.route) {
        TopAppBar(
            title = { },
            navigationIcon = {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.iconagrisynergy),
                        contentDescription = "Logo Aplikasi",
                        modifier = Modifier.size(59.dp),
                        tint = Color.Unspecified
                    )
                }
            },
            actions = {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = {
                        navController.navigate(Screen.Notifikasi.route)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.iconnotiffull),
                            contentDescription = "Notifikasi",
                            modifier = Modifier.size(27.dp),
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate("user_profile")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.iconprofileline),
                            contentDescription = "Profile",
                            modifier = Modifier.size(29.dp),
                            tint = Color.White
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(0xFF13382C),
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
        )
    }
}





@Composable
private fun BottomNavigationBar(
    navController: NavHostController
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
                title = "Forum",
                icon = R.drawable.iconforum,
                screen = Screen.Forum
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
                        modifier = Modifier.size(22.dp),
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = Color.White,
                        fontSize = 10.sp,
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
//    AgrisynergiApp()
}
