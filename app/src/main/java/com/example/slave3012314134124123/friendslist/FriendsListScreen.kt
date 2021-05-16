package com.example.slave3012314134124123.friendslist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.slave3012314134124123.data.models.FriendsListEntry
import com.example.slave3012314134124123.navigationbar.NavigationBarScreen
import com.google.accompanist.coil.CoilImage


@Composable
fun FriendsListScreen(
    youId: Int,
    navController: NavController
){

    Column() {
        FriendsList(navController = navController,youId = youId)

    }
}



@Composable
fun FriendsList(
    youId: Int,
    navController: NavController,
    viewModel: FriendsListViewModel = hiltNavGraphViewModel()

){
    val friendsList by remember { viewModel.friendsList}
    val loadError by remember { viewModel.loadError}
    val isLoading by remember { viewModel.isLoading}

    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        val itemCount = friendsList.size
        Log.e("Info", "FriendsList ${itemCount}")


        var i = 0
        items(friendsList.size){
            if(it >= friendsList.size){
                viewModel.loadFriendsPaginated()
            }
            Log.e("FIO", friendsList[it].fio)
            FriendsRow(rowIndex = it, entries = friendsList, navController = navController, youId = youId)

        }

//        items(itemCount) {
//            if(it >= itemCount - 1) {
//                viewModel.loadFriendsPaginated()
//            }
//            FriendsRow(rowIndex = 0, entries = friendsList, navController = navController)
//
//            Log.e("Info", "FriendsList-2")
//
//        }

    }



}

@Composable
fun FriendsRow(
    youId: Int,
    rowIndex: Int,
    entries: List<FriendsListEntry>,
    navController: NavController
){
    Column() {
        FriendsEntry(
            entry = entries[rowIndex],
            navController = navController,
            youId = youId
        )
        Spacer(modifier = Modifier.height(6.dp))
    }

}

@Composable
fun FriendsEntry(
    youId: Int,
    entry: FriendsListEntry,
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
                            text = "Владелец ${if (entry.masterFio == "") "Нет" else entry.masterFio} ",
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily.SansSerif,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "${entry.slaveLvl.toString()} lvl",
                                color = Color.Blue,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        MoneyStr(silver = entry.priceSilver, gold = entry.priceGold, info = "Стоимость ")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "${entry.defLvl.toString()} lvl",
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
fun MoneyStr(info:String, silver: Int, gold: Int) {
    Row {
        Text(
            text = info, fontWeight = FontWeight(500),
            fontFamily = FontFamily.SansSerif,
        )
        Text(
            text = "SM ${silver} ", color = Color(0xFF4169E1),
            fontWeight = FontWeight(500),
            fontFamily = FontFamily.SansSerif,
        )
        Text(
            text = "GM ${gold}", color = Color(0xFFFFA500), fontWeight = FontWeight(500),
            fontFamily = FontFamily.SansSerif,
        )
    }
}

