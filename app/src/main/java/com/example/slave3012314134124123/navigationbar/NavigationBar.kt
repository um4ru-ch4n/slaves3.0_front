package com.example.slave3012314134124123.navigationbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.slave3012314134124123.friendslist.FriendsList
import java.lang.reflect.Modifier


@Composable
fun NavigationBarScreen(
    navController: NavController,
    //modifier: Modifier
) {
    Column() {


        Row(
        ) {

            Button(onClick = {
                navController.navigate(
                    "user_profile"
                )
            }) {
                Text(text = "Профиль")
            }
            Button(
                onClick = {
                    navController.navigate(
                        "friends_list"
                    )
                }) {
                Text(text = "Друзья")
            }
            Button(onClick = { navController.navigate(
                "rating"
            ) }) {
                Text(text = "Рейтинг")
            }

        }

    }
}