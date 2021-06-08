package com.example.slave3012314134124123.fellowslaves

import com.example.slave3012314134124123.slaveslist.SlavesListViewModel

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
import com.example.slave3012314134124123.data.models.FellowSlavesEntry
import com.example.slave3012314134124123.data.models.Сache
import com.example.slave3012314134124123.data.models.SlavesListEntry
import com.example.slave3012314134124123.slaveslist.TextFetter
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.annotation.meta.When


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FellowSlavesListScreen(
    fellow_id: Int?,
    сache: Сache,
    navController: NavController
){
    FellowSlavesList(navController = navController, сache = сache,fellow_id=fellow_id)
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FellowSlavesList(
    fellow_id: Int?,
    сache: Сache,
    navController: NavController,
    viewModel: FellowSlavesViewModel = hiltNavGraphViewModel()

){
    val (loadList, setLoadList) = remember { mutableStateOf(true) }

    viewModel.user_id.value = сache.fellow_id2!!


    val jsonObject = JSONObject()
    jsonObject.put("user_id", сache.fellow_id2!!)
    val jsonObjectString = jsonObject.toString()

    val buuBodyRequest =
        jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())



    if(loadList) {
        runBlocking {
            viewModel.loadFellowSlavesPaginated(сache.token!!,buuBodyRequest)
        }
        setLoadList(false)
    }
    val slavesList by remember { viewModel.slavesList}
    val loadError by remember { viewModel.loadError}
    val isLoading by remember { viewModel.isLoading}


    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.fillMaxWidth()

    ){
        val itemCount = slavesList.size

        Log.e("FSS", "SIZE: ${itemCount}")

        items(slavesList.size){
//            if(it >= slavesList.size){
//                viewModel.loadFellowSlavesPaginated()
//            }
            FellowSlavesRow(rowIndex = it, entries = slavesList, navController = navController, maxSize = slavesList.size, сache = сache)
        }
        items(1)
        {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FellowSlavesRow(
    сache: Сache,
    rowIndex: Int,
    maxSize: Int,
    entries: List<FellowSlavesEntry>,
    navController: NavController
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        if (rowIndex % 3 == 0) {
            FellowSlavesEntry(
                entry = entries[rowIndex],
                navController = navController,
                сache = сache
            )
            if (maxSize - 1 >= rowIndex + 1)
                FellowSlavesEntry(
                    entry = entries[rowIndex + 1],
                    navController = navController,
                    сache = сache
                )
            if (maxSize - 1 >= rowIndex + 2)
                FellowSlavesEntry(
                    entry = entries[rowIndex + 2],
                    navController = navController,
                    сache = сache
                )
        }

    }
    Spacer(modifier = Modifier.height(6.dp))
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FellowSlavesEntry(
    сache: Сache,
    entry: FellowSlavesEntry,
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
                    "user_profile/${entry.id}"

                )
                сache.fellow_id = entry.id
                сache.fellow_id2 = entry.id

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

