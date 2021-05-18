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
import com.google.accompanist.coil.CoilImage


@Composable
fun FriendsListScreen(
    сache: Сache,
    navController: NavController
){
    Column() {
        FriendsList(navController = navController,сache = сache)

    }
}



@Composable
fun FriendsList(
    сache: Сache,
    navController: NavController,
    viewModel: FriendsListViewModel = hiltNavGraphViewModel()

){

    val friendsList by remember { viewModel.friendsList}
    val loadError by remember { viewModel.loadError}
    val isLoading by remember { viewModel.isLoading}
    viewModel.token2.value = сache.token!!
//    var token = remember {
//      viewModel.token
//    }



    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        val itemCount = friendsList.size


        var i = 0
        items(friendsList.size){
            if(it >= friendsList.size){
                viewModel.loadFriendsPaginated(сache.token!!)
            }
            FriendsRow(rowIndex = it, entries = friendsList, navController = navController, сache = сache )

        }

    }



}

@Composable
fun FriendsRow(
    сache: Сache,
    rowIndex: Int,
    entries: List<FriendsListEntry>,
    navController: NavController,
){
    Column() {
        FriendsEntry(
            сache = сache,
            entry = entries[rowIndex],
            navController = navController,
        )
        Spacer(modifier = Modifier.height(6.dp))
    }

}

@Composable
fun FriendsEntry(
    сache: Сache,
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
                сache.master_fio = entry.masterFio
                сache.master_id = entry.master_Id
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
                            text = "Босс ${if (entry.masterFio == "") "отсутствует" else entry.masterFio} ",
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp
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

