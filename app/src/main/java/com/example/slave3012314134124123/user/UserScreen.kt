package com.example.slave3012314134124123.user

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.example.slave3012314134124123.data.models.FriendsListEntry
import com.example.slave3012314134124123.data.models.UserEntry
import com.example.slave3012314134124123.data.models.YouId
import com.example.slave3012314134124123.data.remote.responses.User
import com.example.slave3012314134124123.navigationbar.NavigationBarScreen
import com.example.slave3012314134124123.skaveslist.SlavesListScreen
import com.example.slave3012314134124123.user.UserViewModel
import com.example.slave3012314134124123.util.Resource
import com.google.accompanist.coil.CoilImage
import org.w3c.dom.Node
import kotlin.properties.ReadWriteProperty


@Composable
fun UserScreen(
    youId: YouId,
    viewModel: UserViewModel = hiltNavGraphViewModel(),
    navController: NavController
) {

    val userInfo = produceState<Resource<User>>(initialValue = Resource.Loading()) {
        value = viewModel.loadUser("THIS TOKEN")
    }

    userInfo.value.data?.let { youId.youId = it.id }

    Column() {


        Surface(
            shape = CutCornerShape(10.dp),
            modifier = androidx.compose.ui.Modifier
                .shadow(4.dp, CutCornerShape(10.dp))
                .fillMaxWidth()
                .fillMaxHeight()
                //.height(135.dp)
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
                        income = it.income
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

                SlavesListScreen(navController = navController)
            }
        }
    }

}

@Composable
fun MoneyBar(silver:Int, gold:Int, income:Int){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, top = 4.dp),

    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            MoneyStr(silver, gold, income)
        }

    }

}

@Composable
fun MoneyStr(silver: Int, gold: Int, income:Int) {
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = "SM ${silver} + ${income}   ", color = Color(0xFF4169E1), fontWeight = FontWeight(700), fontFamily = FontFamily.SansSerif)
        Text(text = "GM ${gold}", color = Color(0xFFFFA500), fontWeight = FontWeight(700), fontFamily = FontFamily.SansSerif)
    }
}