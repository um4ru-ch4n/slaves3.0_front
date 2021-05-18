package com.example.slave3012314134124123.friendslist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.example.slave3012314134124123.data.models.Сache
import com.example.slave3012314134124123.data.models.FriendsListEntry
import com.example.slave3012314134124123.data.remote.responses.FriendsList
import com.example.slave3012314134124123.data.remote.responses.FriendsListItem
import com.example.slave3012314134124123.util.Resource
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.runBlocking


@Composable
fun FriendsListScreen2(
    сache: Сache,
    navController: NavController
){
    Column() {
        FriendsList2(navController = navController,сache = сache)

    }
}



@Composable
fun FriendsList2(
    сache: Сache,
    navController: NavController,
    viewModel: FriendsListViewModel2 = hiltNavGraphViewModel()

){

    val friendsList : Resource<FriendsList>
    runBlocking {
        friendsList = viewModel.loadFriendsPaginated(сache.token!!)
    }
    Log.e("EEEEEEEEEEEEEEEEEEEEEE", " QQQQQ ${friendsList.data?.size}")

    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ) {


        //items(friendsList.data!!.size){


        items(friendsList.data!!.size) {

            FriendsRow2(entries = friendsList.data!![it], navController = navController, сache = сache)

        }



    }



}

@Composable
fun FriendsRow2(
    сache: Сache,
    entries: FriendsListItem,
    navController: NavController,
){
    Column() {
        FriendsEntry2(
            сache = сache,
            entry = entries,
            navController = navController,
        )
        Spacer(modifier = Modifier.height(6.dp))
    }

}

@Composable
fun FriendsEntry2(
    сache: Сache,
    entry: FriendsListItem,
    navController: NavController,
    viewModel: FriendsListViewModel = hiltNavGraphViewModel()
) {

    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.White)
            .clickable {
                navController.navigate(
                    "friend_profile/${entry.id}",

                    )
                сache.master_fio = entry.master_fio
                сache.master_id = entry.master_id
            }
    ) {

        Row(modifier = Modifier.padding(15.dp)) {
            Surface(shape = CircleShape) {
                CoilImage(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data(entry.photo)
                        .target()
                        .build(),
                    contentDescription = entry.fio,
                    fadeIn = true,
                    modifier = Modifier
                        .size(70.dp)
                )
            }

            Box(modifier = Modifier.padding(start = 15.dp)) {

                Column(modifier = Modifier.fillMaxWidth()) {

                    Text(
                        text = entry.fio,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily.SansSerif
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Босс ${if (entry.master_fio == "") "отсутствует" else entry.master_fio} ",
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "${entry.slave_level.toString()} lvl",
                                color = Color.Blue,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        MoneyStr2(silver = entry.purchase_price_sm, gold = entry.purchase_price_gm, info = "Стоимость ")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "${entry.defender_level.toString()} lvl",
                                color = Color.Red,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MoneyStr2(info:String, silver: Int, gold: Int) {
    Row {
        Text(
            text = info, fontWeight = FontWeight(500),
            fontFamily = FontFamily.SansSerif,
            fontSize = 14.sp
        )
        Text(
            text = "SM ${silver} ", color = Color(0xFF4169E1),
            fontWeight = FontWeight(500),
            fontFamily = FontFamily.SansSerif,
            fontSize = 14.sp
        )
        Text(
            text = "GM ${gold}", color = Color(0xFFFFA500), fontWeight = FontWeight(500),
            fontFamily = FontFamily.SansSerif,
            fontSize = 14.sp
        )
    }
}

