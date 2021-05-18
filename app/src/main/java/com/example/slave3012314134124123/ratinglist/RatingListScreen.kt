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
import com.example.slave3012314134124123.friendslist.FriendsListViewModel
import com.google.accompanist.coil.CoilImage

@Composable
fun RatingListScreen(
    сache: Сache,
    navController: NavController
){

    Column() {
        RatingList(navController = navController, сache = сache)

    }
}



@Composable
fun RatingList(
    сache: Сache,
    navController: NavController,
    viewModel: RatingListViewModel = hiltNavGraphViewModel()

){
    val ratingList by remember { viewModel.ratingList}
    val loadError by remember { viewModel.loadError}
    val isLoading by remember { viewModel.isLoading}

    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        val itemCount = ratingList.size


        Log.e("RATING-SIZE",ratingList.size.toString() )
        var i = 0
        items(ratingList.size){

            Log.e("RATING-SIZE",ratingList.size.toString() )

            if(it >= ratingList.size){
                viewModel.loadRatingListPaginated(сache.token!!)
            }
            Log.e("FIO", ratingList[it].fio)
            RatingRow(rowIndex = it, entries = ratingList, navController = navController, сache = сache)

        }
    }



}

@Composable
fun RatingRow(
    сache: Сache,
    rowIndex: Int,
    entries: List<RatingListEntry>,
    navController: NavController
){
    Column() {
        RatingEntry(
            entry = entries[rowIndex],
            navController = navController,
            сache = сache
        )
        Spacer(modifier = Modifier.height(6.dp))
    }

}

@Composable
fun RatingEntry(
    сache: Сache,
    entry: RatingListEntry,
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
                    "user_profile/${entry.id_user}",
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