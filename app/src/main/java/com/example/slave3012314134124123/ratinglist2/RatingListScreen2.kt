package com.example.slave3012314134124123.ratinglist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.example.slave3012314134124123.data.models.Сache
import com.example.slave3012314134124123.data.models.RatingListEntry
import com.example.slave3012314134124123.data.remote.responses.RatingList
import com.example.slave3012314134124123.data.remote.responses.RatingListItem
import com.example.slave3012314134124123.friendslist.FriendsListViewModel
import com.example.slave3012314134124123.ratinglist2.RatingListViewModel2
import com.example.slave3012314134124123.util.Resource
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.runBlocking

@Composable
fun RatingListScreen2(
    сache: Сache,
    navController: NavController
){

    Column() {
        RatingList2(navController = navController, сache = сache)

    }
}



@Composable
fun RatingList2(
    сache: Сache,
    navController: NavController,
    viewModel: RatingListViewModel2 = hiltNavGraphViewModel()

){
    val ratingList : Resource<RatingList>

    runBlocking {
        ratingList = viewModel.loadRatingListPaginated(сache.token!!)
    }

    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        val itemCount = ratingList.data?.size


        Log.e("RATING-SIZE","v " + itemCount.toString() )
        var i = 0
        items(itemCount!!){

            RatingRow2(rowIndex = it, entries = ratingList.data, navController = navController, сache = сache)

        }
    }



}

@Composable
fun RatingRow2(
    сache: Сache,
    rowIndex: Int,
    entries: RatingList,
    navController: NavController
){
    Column() {
        RatingEntry2(
            entry = entries[rowIndex],
            navController = navController,
            сache = сache
        )
        Spacer(modifier = Modifier.height(6.dp))
    }

}

@Composable
fun RatingEntry2(
    сache: Сache,
    entry: RatingListItem,
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
                    "user_profile/${entry.id}",
                )
                //cash.master_id = entry.

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
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.SansSerif
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Работников ${entry.slaves_count}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily.SansSerif)
                    }
                }
            }
        }
    }
}