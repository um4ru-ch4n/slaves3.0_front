package com.example.slave3012314134124123.slaveslist


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.runBlocking
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.annotation.meta.When


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SlavesListScreen(
    сache: Сache,
    navController: NavController
){
    SlavesList(navController = navController, сache = сache)
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SlavesList(
    сache: Сache,
    navController: NavController,
    viewModel: SlavesListViewModel = hiltNavGraphViewModel()

){


    val (loadList, setLoadList) = remember { mutableStateOf(true) }
    if(loadList) {
        runBlocking {
            viewModel.loadSlavesPaginated(сache.token!!)
        }
        setLoadList(false)
    }
    val slavesList by remember { viewModel.slavesList}
    val loadError by remember { viewModel.loadError}
    val isLoading by remember { viewModel.isLoading}




    //Log.e("TOKEN VM-SL", "V ${viewModel.token2.value}")

    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.fillMaxWidth()

    ){
        val itemCount = slavesList.size
        Log.e("Info", "SlaveList ${itemCount}")


        items(slavesList.size){
//            if(it >= slavesList.size){
//                viewModel.loadSlavesPaginated()
//            }
            Log.e("FIO", slavesList[it].fio)
            SlavesRow(rowIndex = it, entries = slavesList, navController = navController, maxSize = slavesList.size)

        }
        items(1)
        {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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



@RequiresApi(Build.VERSION_CODES.O)
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
                Box(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 10.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(60.dp)

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
                                        else -> Color(0xFF3CB371)
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
                                        .size(55.dp)
                                )
                            }



                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                        ) {
                            TextFetter(fetter_type = entry.fetter_type, has_fetter = entry.has_fetter, fetter_time = entry.fetter_time, fetter_duration = entry.fetter_duration)
                        }
                    }
                }


                Text(
                    text = entry.fio,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable fun TextFetter(
    has_fetter: Boolean,
    fetter_type : String,
    fetter_time: String,
    fetter_duration: Int
) {
    val time:String = fetter_time

    val currentDateTimeFetter = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME)

    val currentDateTime = LocalDateTime.now()

    val timeFetter =  Duration.between(currentDateTimeFetter,currentDateTime).toMinutes()-180





    if(has_fetter) {



        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
        ) {

            val color = when (fetter_type) {
                "common" -> Color(0xFF3CB371)
                "uncommon" -> Color(0xFF008B8B)
                "rare" -> Color(0xFF4682B4)
                "epic" -> Color(0xFFDA70D6)
                "immortal" -> Color(0xFFFF8C00)
                "legendary" -> Color(0xFF8B0000)
                else -> Color(0xFF3CB371)
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    //.height(22.dp)
                    .defaultMinSize(24.dp)
                    .background(color)
                    .padding(2.dp)
            ) {
                val str = when (fetter_type) {
                    "common" -> "${fetter_duration-timeFetter}"
                    "uncommon" -> "${fetter_duration-timeFetter}"
                    "rare" -> "${fetter_duration-timeFetter}"
                    "epic" -> "${fetter_duration-timeFetter}"
                    "immortal" -> "${fetter_duration-timeFetter}"
                    "legendary" -> "${fetter_duration-timeFetter}"
                    else -> "0"
                }
                Text(
                    text = str,
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily.SansSerif,
                )
            }
        }



    } else {

        val duration:Long = when (fetter_type) {
            "common" -> 120
            "uncommon" -> 240
            "rare" -> 360
            "epic" -> 480
            "immortal" -> 720
            "legendary" -> 1440
            else -> 0
        }
        Log.e("sdf", "${timeFetter}")
        if(timeFetter*(-1) == duration) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        //.height(22.dp)
                        .defaultMinSize(24.dp)
                        .background(Color(0xFF808080))
                        .padding(2.dp)
                ) {
                    val str = (fetter_duration - timeFetter).toString()

                    Text(
                        text = str,
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.SansSerif,
                    )
                }
            }
        }
    }
}