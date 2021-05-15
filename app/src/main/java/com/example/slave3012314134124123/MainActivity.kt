package com.example.slave3012314134124123


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.slave3012314134124123.fellow.FellowScreen
import com.example.slave3012314134124123.friendslist.FriendsListScreen
import com.example.slave3012314134124123.navigationbar.NavigationBarScreen
import com.example.slave3012314134124123.skaveslist.SlavesListScreen
import com.example.slave3012314134124123.slaveinfo.SlaveInfoScreen
import com.example.slave3012314134124123.ui.theme.Slave3012314134124123Theme
import com.example.slave3012314134124123.user.UserScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Slave3012314134124123Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "friends_list") {
                        composable("user_profile") {
                            //UserScreen(navController = navController)
                            //FriendsListScreen( navController = navController)
                            Column() {


                                UserScreen(navController = navController)
                                SlavesListScreen(navController = navController)
                            }
                        }
                        composable("friends_list") {
                            //UserScreen(navController = navController)
                            Column() {


                                FriendsListScreen(navController = navController)

                            }
                        }
                        composable(
                            "friend_profile/{id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.IntType
                                }
                            )) {
                            val id = remember {
                                it.arguments?.getInt("id")
                            }
                            Log.e("ID", id.toString())
                            FellowScreen(navController = navController, idFellow = id)
                        }
                        composable(
                            "slave_profile/{id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.IntType
                                }
                            )) {
                            val id = remember {
                                it.arguments?.getInt("id")
                            }
                            Log.e("ID", id.toString())
                            SlaveInfoScreen(navController = navController, idFellow = id)
                        }
                    }
                }
            }
        }
    }
}

