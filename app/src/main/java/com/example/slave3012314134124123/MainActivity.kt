package com.example.slave3012314134124123


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.findNavController
import com.example.slave3012314134124123.auth.AuthScreen
import com.example.slave3012314134124123.data.models.Сache
import com.example.slave3012314134124123.fellow.FellowScreen
import com.example.slave3012314134124123.friendslist.FriendsListScreen
import com.example.slave3012314134124123.friendslist.FriendsListScreen2
import com.example.slave3012314134124123.navigationbar.NavigationBarScreen
import com.example.slave3012314134124123.ratinglist.RatingListScreen
import com.example.slave3012314134124123.ratinglist.RatingListScreen2
import com.example.slave3012314134124123.slaveinfo.SlaveInfoScreen
import com.example.slave3012314134124123.ui.theme.Slave3012314134124123Theme
import com.example.slave3012314134124123.user.UserScreen
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var prefs : SharedPreferences

    var navController = NavController(this)
    val сache: Сache = Сache(0,0,0,"", "")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Slave3012314134124123Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    //qwe()
                    //prefs =
                    //saveToken("")
                    prefs = getSharedPreferences("token", Context.MODE_PRIVATE)
                    Log.e("Save token", "${prefs.getString("token", "")}")

                    сache.token = prefs.getString("token", "")

                    var path : String = "auth"
                    if(prefs.getString("token", "")!= "") path = "user_profile"

                    navController = rememberNavController()
                    NavHost(navController = navController as NavHostController, startDestination = path) {
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
                                        UserScreen(navController = navController, сache = сache, activity = this@MainActivity)
                                    }

                                }

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter),
                                ) {
                                    NavigationBarScreen(navController = navController, сache = сache)
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
                                        сache = сache
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter),
                                ) {
                                    NavigationBarScreen(navController = navController, сache = сache)
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

                                    RatingListScreen2(navController = navController, сache = сache)
                                }

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter),
                                ) {
                                    NavigationBarScreen(navController = navController,сache = сache )
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
                                        path = "rating",
                                        сache = сache
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter),
                                ) {
                                    NavigationBarScreen(navController = navController, сache = сache)
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
                                        path = "friends_list",
                                        сache = сache
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter),
                                ) {
                                    NavigationBarScreen(navController = navController, сache = сache)
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
                                        сache = сache
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter),
                                ) {
                                    NavigationBarScreen(navController = navController, сache = сache)
                                }
                            }
                        }
                        composable("auth"){
                            saveToken("")
                            AuthScreen( activity = this@MainActivity, navController = navController)
                        }
                    }
                }
            }
        }
    }

    fun saveToken(token: String)
    {
        сache.token = ""
        val editor = prefs.edit()
        editor.putString ("token", token).apply()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Log.e("TOKEN", "${token.accessToken}")
                saveToken(token.accessToken)
                сache.token = prefs.getString("token", "")
                navController.navigate(
                    "user_profile"
                )
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(this@MainActivity,"Ошибка ${errorCode}", Toast.LENGTH_LONG)
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}


