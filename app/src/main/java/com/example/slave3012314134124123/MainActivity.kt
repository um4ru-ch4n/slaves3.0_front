package com.example.slave3012314134124123


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.slave3012314134124123.data.models.YouId
import com.example.slave3012314134124123.fellow.FellowScreen
import com.example.slave3012314134124123.friendslist.FriendsListScreen
import com.example.slave3012314134124123.navigationbar.NavigationBarScreen
import com.example.slave3012314134124123.ratinglist.RatingListScreen
import com.example.slave3012314134124123.skaveslist.SlavesListScreen
import com.example.slave3012314134124123.slaveinfo.SlaveInfoScreen
import com.example.slave3012314134124123.ui.theme.Slave3012314134124123Theme
import com.example.slave3012314134124123.user.MoneyBar
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

                    var youId = YouId(0, 0, 0, 0, 0)
                    val (masterFio, setMasterFio) = remember { mutableStateOf("") }
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "user_profile") {
                        composable("user_profile") {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()

                            ) {


                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                                ) {
                                    Column() {
                                        UserScreen(navController = navController, youId = youId)

                                    }

                                }

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter),
                                ) {
                                    NavigationBarScreen(navController = navController)
                                }

                            }

                        }
                        composable("friends_list") {

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()

                            ) {

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                                ) {

                                    FriendsListScreen(
                                        navController = navController,
                                        youId = youId.youId,
                                        masterFio = masterFio,
                                        setMasterFio = setMasterFio
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter),
                                ) {
                                    NavigationBarScreen(navController = navController)
                                }

                            }

                        }
                        composable("rating") {

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()

                            ) {

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                                ) {

                                    RatingListScreen(navController = navController)
                                }

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter),
                                ) {
                                    NavigationBarScreen(navController = navController)
                                }

                            }

                        }
                        composable("user_profile/{id}", arguments = listOf(
                            navArgument("id")
                            {
                                type = NavType.IntType
                            })) {
                            val id = remember {
                                it.arguments?.getInt("id")
                            }
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()

                            ) {

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                                ) {

                                    FellowScreen(
                                        navController = navController,
                                        idFellow = id,
                                        youId = youId.youId,
                                        path = "rating",
                                        masterFio = masterFio
                                    )
                                }
                            }
                        }
                        composable("friend_profile/{id}", arguments = listOf(navArgument("id") {
                            type = NavType.IntType
                        })) {
                            val id = remember {
                                it.arguments?.getInt("id")
                            }
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()

                            ) {

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                                ) {

                                    FellowScreen(
                                        navController = navController,
                                        idFellow = id,
                                        youId = youId.youId,
                                        path = "friends_list",
                                        masterFio = masterFio
                                    )
                                }
                            }
                        }
                        composable(
                            "slave_profile/{id}",
                            arguments = listOf(navArgument("id") {
                                type = NavType.IntType
                            })
                        ) {
                            val id = remember {
                                it.arguments?.getInt("id")
                            }

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()

                            ) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                                ) {

                                    SlaveInfoScreen(
                                        navController = navController,
                                        idFellow = id,
                                        youId = youId.youId
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


