package com.example.slave3012314134124123.user

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.example.slave3012314134124123.data.models.Сache
import com.example.slave3012314134124123.data.remote.responses.User
import com.example.slave3012314134124123.slavelist2.SlavesListScreen2
import com.example.slave3012314134124123.slaveslist.SlavesListScreen
import com.example.slave3012314134124123.util.Resource
import com.google.accompanist.coil.CoilImage


@Composable
fun UserScreen(
    activity: Activity,
    сache : Сache,
    viewModel: UserViewModel = hiltNavGraphViewModel(),
    navController: NavController
) {


    val userInfo = produceState<Resource<User>>(initialValue = Resource.Loading()) {
        value = viewModel.loadUser(сache.token!!)
    }

    if(userInfo.value.message != ""){
        Toast.makeText(activity,"Ошибка ${userInfo.value.message.toString()}", Toast.LENGTH_LONG)

//        navController.navigate(
//            "auth"
//        )
    }

    userInfo.value.data?.let {
        сache.user_id = it.id
    }

    Column() {


        Surface(
            shape = CutCornerShape(10.dp),
            modifier = androidx.compose.ui.Modifier
                .shadow(4.dp, CutCornerShape(10.dp))
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
                .padding(top = 5.dp)
        ) {

            Column(
                modifier = Modifier
                //.padding(start = 5.dp,end = 5.dp)
            ) {


                userInfo.value.data?.let {
                    MoneyBar(
                        silver = it.balance,
                        gold = it.gold,
                        income = it.income,
                        navController = navController
                    )
                }
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.White)

                ) {
                    Row(modifier = androidx.compose.ui.Modifier.padding(15.dp)) {
                        Surface(shape = CircleShape) {
                            CoilImage(
                                request = ImageRequest.Builder(LocalContext.current)
                                    .data(userInfo.value.data?.photo)
                                    .target()
                                    .build(),
                                contentDescription = userInfo.value.data?.fio,
                                fadeIn = true,
                                modifier = androidx.compose.ui.Modifier
                                    .size(70.dp)
                            )

                        }
                        Box(modifier = androidx.compose.ui.Modifier.padding(start = 15.dp)) {
                            Column(modifier = androidx.compose.ui.Modifier.fillMaxWidth()) {
                                userInfo.value.data?.let {
                                    Text(
                                        text = it.fio,
                                        fontWeight = FontWeight(700),
                                        fontFamily = FontFamily.SansSerif,
                                        fontSize = 18.sp
                                    )
                                }
                                userInfo.value.data?.let {
                                    Text(
                                        text = "Босс ${it.master_id}",
                                        fontWeight = FontWeight(500),
                                        fontFamily = FontFamily.SansSerif,
                                    )
                                }
                                userInfo.value.data?.let {
                                    Text(
                                        text = "Работников ${it.slaves_count}",
                                        fontWeight = FontWeight(500),
                                        fontFamily = FontFamily.SansSerif,
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                SlavesListScreen2(navController = navController, сache = сache)
            }
        }
    }

}

@Composable
fun MoneyBar(silver:Int, gold:Int, income:Int, navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, top = 4.dp),

        ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            MoneyStr(silver, gold, income, navController = navController)

        }

    }

}


@Composable
fun MoneyStr(silver: Int, gold: Int, income:Int, navController: NavController) {
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)

        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 5.dp, end = 5.dp, top = 0.dp)
            ) {

                Row() {
                    Text(
                        text = "SM ${silver} + ${income}   ",
                        color = Color(0xFF4169E1),
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = "GM ${gold}",
                        color = Color(0xFFFFA500),
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(start = 5.dp, end = 5.dp, top = 0.dp)
            ) {
                Surface(
                    shape = CutCornerShape(5.dp),
                    modifier = androidx.compose.ui.Modifier
                        .shadow(4.dp, CutCornerShape(3.dp))
                        .background(Color.White)
                        .padding(4.dp)
                        .height(20.dp)
                        .width(50.dp)
                        .clickable {
                            navController.navigate(
                                "auth"

                            )
                        }
                ) {
                    Box( modifier = Modifier
                        .padding(start = 4.dp)) {
                        Text(
                            text = "выйти",
                            fontWeight = FontWeight(500),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}