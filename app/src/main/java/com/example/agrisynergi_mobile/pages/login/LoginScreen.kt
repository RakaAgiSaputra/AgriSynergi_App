package com.example.agrisynergi_mobile.pages.login

import android.content.Context
import android.widget.Toast
import com.example.agrisynergi_mobile.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrisynergi_mobile.navigation.Screen

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(false) }
    val statusLogin by remember { mutableStateOf("Anda berhasil login") }
    var statusError by remember { mutableStateOf(false) }
//    var credentialUserList: List = remember { mutableStateListOf<userList>() }
    val context: Context = LocalContext.current

//    var showDialog by remember { mutableStateOf(false) }
//    val image_group = painterResource(R.drawable.group_33845__1__1)
//    Image(
//        painter = image_group,
//        contentDescription = null,
//        contentScale = ContentScale.Fit,
//        modifier = Modifier.fillMaxWidth()
//    )

    Box(modifier = Modifier.background(Color.White)){
        Column (modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center){
            Text("Welcome bro, here you must login before start your activity", fontSize = 20.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center)
            Spacer(Modifier.height(20.dp))
            val image = painterResource(R.drawable.agri_synergy_3)
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth().size(200.dp).padding(8.dp)
            )

            if(isLogin){
                statusError = false
                Text(text = statusLogin, textAlign = TextAlign.Center, color = Color.Green, modifier = Modifier.align(
                    Alignment.CenterHorizontally) )
            }
            if(statusError){
                isLogin = false
                Text("Incorect username or password\n please try again", color = Color.Red, textAlign = TextAlign.Center, modifier = Modifier.align(
                    Alignment.CenterHorizontally))
            }


            // Menampilkan AlertDialog jika login berhasil
//            if (showDialog) {
//                AlertIsLoggedSection(
//                    onDismiss = { showDialog = false },
//                    onConfirm = { showDialog = false } // Menutup dialog saat tombol OK ditekan
//                )
//            }

            Text("Username", fontWeight = FontWeight.Medium)
            OutlinedTextField(value = username, onValueChange = {
                username = it
            }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Text("Password",fontWeight = FontWeight.Medium)
            OutlinedTextField(value = password, onValueChange = {
                password = it
            }, singleLine = true ,visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
            Text("Forgot password?",fontSize = 12.sp, color = Color.Blue, modifier = Modifier.clickable(onClick = {
                 navController.navigate(Screen.ForgetPass.route)
            }))


            Button(onClick = {
                if(username == "admin"  && password == "123"){
                    isLogin = true
                    Toast.makeText(context,"Login berhasil", Toast.LENGTH_LONG).show()
//                    navigationToSecondScreen()
                    navController.navigate(Screen.Beranda.route)
//                    showDialog = true
                }else{
                    statusError = true
                    isLogin = false
                }
            }, modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color(0xFFFFA600)
            )) {
                Text("Login")
            }
            val text = buildAnnotatedString {
                append("Don't have an account? ")
                withStyle(style = androidx.compose.ui.text.SpanStyle(color = Color.Blue)) {
                    append("Register here")
                }
            }

            Text(text = text, fontSize = 12.sp,modifier = Modifier.align(Alignment.CenterHorizontally))
            Text("Or login with",fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))

            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                val image2: Painter = painterResource(
                    R.drawable.google_1
                )

                Image(
                    painter = image2,
                    contentDescription = null
                )

            }
        }
    }
}


//@Composable
//fun AlertIsLoggedSection(onDismiss: () -> Unit, onConfirm: () -> Unit) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        confirmButton = { },
//        text = { Text("Anda Berhasil Login") }
//    )
//}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}
