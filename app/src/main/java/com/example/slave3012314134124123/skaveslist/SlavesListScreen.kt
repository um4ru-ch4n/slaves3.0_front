package com.example.slave3012314134124123.skaveslist


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
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
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.example.slave3012314134124123.data.models.FriendsListEntry
import com.example.slave3012314134124123.data.models.SlavesListEntry
import com.example.slave3012314134124123.data.models.UserEntry
import com.example.slave3012314134124123.fellow.FellowViewModel
import com.example.slave3012314134124123.friendslist.FriendsListViewModel
import com.example.slave3012314134124123.user.UserViewModel
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.runBlocking


@Composable
fun SlavesListScreen(
    navController: NavController
){
    SlavesList(navController = navController)
}



@Composable
fun SlavesList(
    navController: NavController,
    viewModel: SlavesListViewModel = hiltNavGraphViewModel()

){
    val slavesList by remember { viewModel.slavesList}
    val loadError by remember { viewModel.loadError}
    val isLoading by remember { viewModel.isLoading}

    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.fillMaxWidth()

    ){
        val itemCount = slavesList.size
        Log.e("Info", "SlaveList ${itemCount}")


        items(slavesList.size){
            if(it >= slavesList.size){
                viewModel.loadSlavesPaginated()
            }
            Log.e("FIO", slavesList[it].fio)
            SlavesRow(rowIndex = it, entries = slavesList, navController = navController, maxSize = slavesList.size)

        }
    }


}

@Composable
fun SlavesRow(
    rowIndex: Int,
    maxSize: Int,
    entries: List<SlavesListEntry>,
    navController: NavController
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        if (rowIndex % 3 == 0) {
            SlavesEntry(
                entry = entries[rowIndex],
                navController = navController
            )
            if (maxSize - 1 >= rowIndex + 1)
                SlavesEntry(
                    entry = entries[rowIndex + 1],
                    navController = navController
                )
            if (maxSize - 1 >= rowIndex + 2)
                SlavesEntry(
                    entry = entries[rowIndex + 2],
                    navController = navController
                )
        }

    }
    Spacer(modifier = Modifier.height(6.dp))
}



@Composable
fun SlavesEntry(
    entry: SlavesListEntry,
    navController: NavController,
    viewModel: SlavesListViewModel = hiltNavGraphViewModel(),
) {

    Surface(

        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .width(100.dp)
            .height(170.dp)
            .background(Color.White),


        ) {
        Box(
            modifier = Modifier.clickable {
                navController.navigate(
                    "slave_profile/${entry.id}"
                )
            }
        ) {
            Column() {

                Surface(
                    shape = CircleShape,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 10.dp)
                ) {
                    CoilImage(
                        request = ImageRequest.Builder(LocalContext.current)
                            .data(entry.photo)
                            .target()
                            .build(),
                        contentDescription = entry.fio,
                        fadeIn = true,
                        modifier = Modifier
                            .size(55.dp)
                    )
                }



                Text(
                    text = entry.fio,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = "lvl ${entry.slave_level}",
                        color = Color.Blue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        fontFamily = FontFamily.SansSerif,
                    )
                    Text(
                        text = "  lvl ${entry.defender_level}",
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        fontFamily = FontFamily.SansSerif,
                    )
                }
                Text(
                    text = "+${entry.profit}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .align(CenterHorizontally)
                )
                Text(
                    text = "${if (entry.job_name != "") entry.job_name else "Нет"}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .align(CenterHorizontally)
                )
            }


        }

    }
}
