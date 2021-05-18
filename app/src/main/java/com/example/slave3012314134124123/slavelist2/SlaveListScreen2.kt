package com.example.slave3012314134124123.slavelist2

import com.example.slave3012314134124123.slaveslist.SlavesListViewModel
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.example.slave3012314134124123.data.models.SlavesListEntry
import com.example.slave3012314134124123.data.remote.responses.SlavesList
import com.example.slave3012314134124123.data.remote.responses.SlavesListItem
import com.example.slave3012314134124123.slaveslist.SlavesListViewModel2
import com.example.slave3012314134124123.util.Resource
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.runBlocking


@Composable
fun SlavesListScreen2(
    сache: Сache,
    navController: NavController
){
    SlavesList2(navController = navController, сache = сache)
}



@Composable
fun SlavesList2(
    сache: Сache,
    navController: NavController,
    viewModel: SlavesListViewModel2 = hiltNavGraphViewModel()

){
    val slavesList: Resource<SlavesList>
    runBlocking {
        slavesList = viewModel.loadSlavesPaginated(сache.token!!)
    }

    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.fillMaxWidth()

    ){

        //val itemCount = slavesList.size
        //Log.e("Info", "SlaveList ${itemCount}")
        Log.e("QQQQQQQQQQQQQQQ", "V ${slavesList.data!!.size}")

        items(slavesList.data!!.size){


            SlavesRow2(rowIndex = it, entries = slavesList.data, navController = navController, maxSize = slavesList.data!!.size)

        }
    }
}

@Composable
fun SlavesRow2(
    rowIndex: Int,
    maxSize: Int,
    entries: SlavesList,
    navController: NavController
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        if (rowIndex % 3 == 0) {
            SlavesEntry2(
                entry = entries[rowIndex],
                navController = navController
            )
            if (maxSize - 1 >= rowIndex + 1)
                SlavesEntry2(
                    entry = entries[rowIndex + 1],
                    navController = navController
                )
            if (maxSize - 1 >= rowIndex + 2)
                SlavesEntry2(
                    entry = entries[rowIndex + 2],
                    navController = navController
                )
        }

    }
    Spacer(modifier = Modifier.height(6.dp))
}



@Composable
fun SlavesEntry2(
    entry: SlavesListItem,
    navController: NavController,
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
