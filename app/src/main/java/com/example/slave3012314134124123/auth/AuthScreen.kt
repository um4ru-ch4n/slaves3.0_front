package com.example.slave3012314134124123.auth

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

@Composable
fun AuthScreen(
    navController : NavController,
    activity: Activity
){

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()

    ) {

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 5.dp, end = 5.dp, top = 5.dp)
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "SLAVE 3.0",
                    fontSize = 21.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    VK.login(activity, arrayListOf(VKScope.FRIENDS, VKScope.PHOTOS ,
                        VKScope.STATUS,
                        VKScope.STATS,
                        VKScope.PHOTOS,
                        VKScope.OFFLINE))
                    navController.navigate(
                        "user_profile"
                    )

                }) {
                    Text(text = "Войти через ВК")
                }


            }

        }
    }
}