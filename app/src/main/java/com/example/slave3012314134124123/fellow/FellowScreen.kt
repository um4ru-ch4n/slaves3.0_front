package com.example.slave3012314134124123.fellow

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.example.slave3012314134124123.MainActivity
import com.example.slave3012314134124123.data.models.Сache
import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.fellowslaves.FellowSlavesListScreen
import com.example.slave3012314134124123.friendslist.MoneyStr
import com.example.slave3012314134124123.slaveinfo.FullScreenDialog
import com.example.slave3012314134124123.slaveinfo.FullScreenDialogRoute
import com.example.slave3012314134124123.slaveslist.TextFetter
import com.example.slave3012314134124123.util.Resource
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FellowScreen (
    activity: MainActivity,
    path: String,
    сache: Сache,
    viewModel: FellowViewModel = hiltNavGraphViewModel(),
    idFellow: Int?,
    navController: NavController
) {

    val jsonObject = JSONObject()
    jsonObject.put("user_id", idFellow!!)
    val jsonObjectString = jsonObject.toString()

    val fellowBodyRequest =
        jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

    val userInfo = produceState<Resource<Fellow>>(initialValue = Resource.Loading()) {
        value = viewModel.loadFellow(сache.token!!, fellowBodyRequest)
    }
    val (showJob, setShowDialog) = remember { mutableStateOf(false) }
    val (showRoute, setShowDialogRoute) = remember { mutableStateOf(false) }

    Surface(
        shape = CutCornerShape(10.dp),
        modifier = androidx.compose.ui.Modifier
            .shadow(4.dp, CutCornerShape(10.dp))
            .fillMaxWidth()
            .fillMaxHeight()
            //.height(135.dp)
            .background(Color.White)
            .padding(top = 5.dp)
    ) {
        Column() {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(105.dp)
                    .background(Color.White)

            ) {
                Row(modifier = Modifier.padding(15.dp)) {
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
                                var flag: Boolean = false
                                var type: String = "common"
                                userInfo.value.data?.let {
                                    flag = it.has_fetter
                                    type = it.fetter_type
                                }

                                if (flag) {
                                    val color = when (type) {
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
                                        .data(userInfo.value.data?.photo)
                                        .target()
                                        .build(),
                                    contentDescription = userInfo.value.data?.fio,
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

                            var flag: Boolean = false
                            var type: String = "common"
                            var time: String = "1971-11-03T00:00:00Z"
                            var duration: Int = 0
                            userInfo.value.data?.let {
                                flag = it.has_fetter
                                type = it.fetter_type
                                time = it.fetter_time
                                duration = it.fetter_duration
                            }
                            TextFetter(
                                fetter_type = type,
                                has_fetter = flag,
                                fetter_time = time,
                                fetter_duration = duration
                            )
                        }
                    }
                    Box(modifier = Modifier.padding(start = 15.dp)) {

                        Column(modifier = Modifier.fillMaxWidth()) {
                            userInfo.value.data?.let {
                                Text(
                                    text = it.fio,
                                    fontWeight = FontWeight(700),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp
                                )
                                if (сache.user_id == it.master_id) {


                                    Text(
                                        text = "Работа ${if (it.job_name != "") it.job_name else "отсутствует"}",
                                        fontWeight = FontWeight(500),
                                        fontFamily = FontFamily.SansSerif,
                                        fontSize = 14.sp
                                    )
                                    MoneyStr(
                                        silver = it.sale_price_sm,
                                        gold = it.sale_price_gm,
                                        info = "Продажа "
                                    )
                                } else {
                                    Text(
                                        text = "Босс ${if (it.master_fio != "") it.master_fio else "отсутствует"}",
                                        fontWeight = FontWeight(500),
                                        fontFamily = FontFamily.SansSerif,
                                        fontSize = 14.sp
                                    )
                                    MoneyStr(
                                        silver = it.purchase_price_sm,
                                        gold = it.purchase_price_gm,
                                        info = "Стоимость "
                                    )
                                }
                            }
                        }
                    }

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (сache.user_id == userInfo.value.data?.master_id && сache.user_id != userInfo.value.data?.id) {
                    Button(onClick = {
                        runBlocking {


                            val jsonObject = JSONObject()
                            jsonObject.put("slave_id", userInfo.value.data!!.id)
                            val jsonObjectString = jsonObject.toString()

                            val buuBodyRequest =
                                jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                            val stringInfo =
                                viewModel.saleFellow(сache.token!!,buuBodyRequest)
                                    .toString()

                        }
                        navController.navigate(path,)
                    }) {
                        Text(text = "Продать")
                    }
                    Button(onClick = {
                        var flag:Boolean = false
                        var duration:Int = 0
                        var time:String = ""
                        userInfo.value.data?.let {
                            flag = it.has_fetter
                            time = it.fetter_time
                            duration = it.fetter_duration
                        }
                        val currentDateTimeFetter = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME)
                        val currentDateTime = LocalDateTime.now()
                        val timeFetter =  Duration.between(currentDateTimeFetter,currentDateTime).toMinutes()-180
                        if(!flag) {

                            if(timeFetter>=duration*duration) {
                                setShowDialogRoute(true)
                            }
                        }else {
                            if (timeFetter < duration * duration) {
                                Log.e("adsf", "asdfg")
                                Toast.makeText(
                                    activity,
                                    "Дождитесь перезарядки цепей",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }) {
                        Text(text = "Цепи")
                    }
                    Button(onClick = {
                        setShowDialog(true)
                    }) {
                        Text(text = "Назначить")
                    }
                } else if (сache.user_id != userInfo.value.data?.id) {
                    Button(onClick = {
                        val jsonObject = JSONObject()
                        jsonObject.put("slave_id", userInfo.value.data!!.id)
                        val jsonObjectString = jsonObject.toString()

                        val buuBodyRequest =
                            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                        runBlocking {


                            val stringInfo = viewModel.buyFellow(
                                сache.token!!,
                                buuBodyRequest
                            ).message.toString()
                        }
                        navController.navigate(path,)

                    }) {
                        Text(text = "Купить")
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            FellowSlavesListScreen(
                fellow_id = userInfo.value.data?.id,
                сache = сache,
                navController = navController
            )

            FullScreenDialog(
                сache,
                navController,
                viewModel,
                userInfo.value.data?.id,
                showJob,
                setShowDialog
            )
            FullScreenDialogRoute(
                сache,
                navController,
                viewModel,
                userInfo.value.data?.id,
                showRoute,
                userInfo.value.data?.has_fetter,
                setShowDialogRoute
            )

        }
    }

}


