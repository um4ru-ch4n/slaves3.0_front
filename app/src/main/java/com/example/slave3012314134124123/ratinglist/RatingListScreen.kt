package com.example.slave3012314134124123.ratinglist

import android.os.Build
import android.util.Log
import android.widget.Space
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.Alignment
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
import com.example.slave3012314134124123.data.models.RatingListEntry
import com.example.slave3012314134124123.friendslist.FriendsListViewModel
import com.example.slave3012314134124123.slaveslist.TextFetter
import com.google.accompanist.coil.CoilImage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RatingListScreen(
    сache: Сache,
    navController: NavController
){

    Column() {
        RatingList(navController = navController, сache = сache)

    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RatingList(
    сache: Сache,
    navController: NavController,
    viewModel: RatingListViewModel = hiltNavGraphViewModel()

){
    val ratingList by remember { viewModel.ratingList}
    val loadError by remember { viewModel.loadError}
    val isLoading by remember { viewModel.isLoading}
    viewModel.token2.value = сache.token!!
    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        val itemCount = ratingList.size


        Log.e("RATING-SIZE",ratingList.size.toString() )
        items(ratingList.size){

            Log.e("RATING-SIZE",ratingList.size.toString() )

            if(it >= ratingList.size){
                viewModel.loadRatingListPaginated(сache.token!!)
            }
            Log.e("FIO", ratingList[it].fio)
            RatingRow(rowIndex = it, entries = ratingList, navController = navController, сache = сache, items = it)

        }
        items(1)
        {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }



}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RatingRow(
    items: Int,
    сache: Сache,
    rowIndex: Int,
    entries: List<RatingListEntry>,
    navController: NavController
) {
    Column() {
        RatingEntry(
            entry = entries[rowIndex],
            navController = navController,
            сache = сache,
            items = items
        )
        Spacer(modifier = Modifier.height(6.dp))
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RatingEntry(
    items:Int,
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
            .height(105.dp)
            .background(Color.White)
            .clickable {
                navController.navigate(
                    "user_profile/${entry.id_user}",
                )
                сache.fellow_id = entry.id_user
                сache.fellow_id2 = entry.id_user
                //cash.master_id = entry.

            }
    ) {

        Row(modifier = Modifier.padding(start=15.dp, bottom = 15.dp, top = 15.dp, end = 15.dp)) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxHeight()
            ) {


                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                ) {

                    Text(
                        "${items + 1}",
                        fontSize = 26.sp,
                        fontWeight = FontWeight(800),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center
                    )

                }
            }
            Spacer(Modifier.width(10.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(75.dp)

            ) {


                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {


                    Surface(
                        shape = CircleShape,
                        modifier = Modifier
                        //.align(CenterHorizontally)
                        //.padding(top = 10.dp)
                    ) {
                        if (entry.has_fetter) {
                            val color = when (entry.fetter_type) {
                                "common" -> Color(0xFF3CB371)
                                "uncommon" -> Color(0xFF008B8B)
                                "rare" -> Color(0xFF4682B4)
                                "epic" -> Color(0xFFDA70D6)
                                "immortal" -> Color(0xFFFF8C00)
                                "legendary" -> Color(0xFF8B0000)
                                else -> Color(0xFF808080)
                            }
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .background(color)
                            ) {}
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier
                        //.align(CenterHorizontally)
                        //.padding(top = 10.dp)
                    ) {

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
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                ) {
                    TextFetter(fetter_type = entry.fetter_type, has_fetter = entry.has_fetter, fetter_duration = entry.fetter_duration, fetter_time = entry.fetter_time)
                }
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
                        Text(
                            text = "Работников ${entry.slaves_count}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }
            }
        }
    }
}