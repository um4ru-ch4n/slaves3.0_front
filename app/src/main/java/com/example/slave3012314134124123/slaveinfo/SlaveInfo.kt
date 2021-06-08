package com.example.slave3012314134124123.slaveinfo

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.example.slave3012314134124123.MainActivity
import com.example.slave3012314134124123.data.models.Сache
import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.fellow.FellowViewModel
import com.example.slave3012314134124123.friendslist.MoneyStr
import com.example.slave3012314134124123.slaveslist.TextFetter
import com.example.slave3012314134124123.util.Resource
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SlaveInfoScreen(
    viewModel: FellowViewModel = hiltNavGraphViewModel(),
    idFellow: Int?,
    cache: Сache,
    activity: MainActivity,
    navController: NavController
) {

    val jsonObject = JSONObject()
    jsonObject.put("user_id", idFellow!!)
    val jsonObjectString = jsonObject.toString()

    val fellowBodyRequest =
        jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())



    val slaveInfo = produceState<Resource<Fellow>>(initialValue = Resource.Loading()) {
        value = viewModel.loadFellow(cache.token!!, fellowBodyRequest)
    }




    val (showJob, setShowDialog) = remember { mutableStateOf(false) }
    val (showRoute, setShowDialogRoute) = remember { mutableStateOf(false) }
    Surface(
        shape = CutCornerShape(10.dp),
        modifier = Modifier
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

                                var flag:Boolean = false
                                var type:String = "common"
                                slaveInfo.value.data?.let {
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
                                        .data(slaveInfo.value.data?.photo)
                                        .target()
                                        .build(),
                                    contentDescription = slaveInfo.value.data?.fio,
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

                            var flag:Boolean = false
                            var type:String = "common"
                            var time:String = "1971-11-03T00:00:00Z"
                            var duration:Int = 0
                            slaveInfo.value.data?.let {
                                flag = it.has_fetter
                                type = it.fetter_type
                                time = it.fetter_time
                                duration = it.fetter_duration
                            }
                            TextFetter(fetter_type = type, has_fetter = flag, fetter_time = time, fetter_duration = duration)
                        }
                    }
                    Box(modifier = Modifier.padding(start = 15.dp)) {

                        Column(modifier = Modifier.fillMaxWidth()) {
                            slaveInfo.value.data?.let {
                                Text(
                                    text = it.fio,
                                    fontWeight = FontWeight(700),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Работа ${if (it.job_name != "") it.job_name else "отсутствует"}",
                                    fontWeight = FontWeight(500),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 14.sp
                                )
                                if (cache.user_id == slaveInfo.value.data?.master_id) {
                                    MoneyStr(
                                        silver = it.sale_price_sm,
                                        gold = it.sale_price_gm,
                                        info = "Продажа "
                                    )
                                } else {
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


            //Log.e("SLAVE", "SLAVE ID ${slaveInfo.value.data!!.id}")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                //if (cash.user_id == slaveInfo.value.data?.master_id) {
                Button(onClick = {
                    runBlocking {

                        val jsonObject = JSONObject()
                        jsonObject.put("slave_id",slaveInfo.value.data!!.id)
                        val jsonObjectString = jsonObject.toString()

                        val buuBodyRequest =
                            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


                        val stringInfo =
                            viewModel.saleFellow(cache.token!!,buuBodyRequest )
                                .toString()
                        Log.e("BUY", stringInfo)

                    }
                    navController.navigate("user_profile",)
                }) {
                    Text(text = "Продать")
                }
                Button(onClick = {
                    var flag:Boolean = false
                    var duration:Int = 0
                    var time:String = ""
                    slaveInfo.value.data?.let {
                        flag = it.has_fetter
                        time = it.fetter_time
                        duration = it.fetter_duration
                    }
                    //time:String = time
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
            }
            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                Surface(

                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(10.dp))
                        .width(150.dp)
                        .height(200.dp)
                        .background(Color.White)
                        .padding(start = 5.dp)


                ) {
                    Column() {

                        Text(
                            text = "Статистика работника",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Добыча +${slaveInfo.value.data?.profit}",
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Опыт ${slaveInfo.value.data?.money_quantity}",
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp
                        )
                    }

                }

                Surface(

                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(10.dp))
                        .width(150.dp)
                        .height(200.dp)
                        .background(Color.White)
                        .padding(start = 5.dp)


                ) {
                    Column() {


                        Text(
                            text = "Статистика гладиатора",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Здоровье ${slaveInfo.value.data?.hp}",
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp
                        )

                        Text(
                            text = "Урон ${slaveInfo.value.data?.damage}",
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Опыт ${slaveInfo.value.data?.damage_quantity}",
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp
                        )

                    }
                }
            }

            FullScreenDialog(
                cache,
                navController,
                viewModel,
                slaveInfo.value.data?.id,
                showJob,
                setShowDialog
            )

            FullScreenDialogRoute(
                cache,
                navController,
                viewModel,
                slaveInfo.value.data?.id,
                showRoute,
                slaveInfo.value.data?.has_fetter,
                setShowDialogRoute
            )
        }
    }
}

@Composable
fun FullScreenDialog(сache: Сache, navController: NavController, viewModel: FellowViewModel ,id:Int?,showDialog:Boolean,setShowDialog: (Boolean)->Unit) {

    var jobText by remember { mutableStateOf("") }


    if (showDialog) {
        Dialog(onDismissRequest = {}) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(300.dp)
                    .height(160.dp)
                    .background(Color.White)
                    .padding(bottom = 7.dp)
            ) {

                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)

                ) {
                    Text(
                        text = "Установите работу", fontWeight = FontWeight(600),
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 18.sp,
                    )

                }

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        //.padding(start = 5.dp, end = 5.dp, top = 5.dp)

                ) {

                    OutlinedTextField(
                        value = jobText,
                        maxLines = 1,

                        onValueChange = { jobText = it },

                        //modifier = Modifier.height(40.dp)
                    )

                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                setShowDialog(false)
                                navController.navigate("user_profile",)
                            },
                        ) {
                            Text("Отмена")
                        }
                        Button(
                            onClick = {
                                setShowDialog(false)
                                Log.e("SET JOB1", "work")


                                runBlocking {

                                    val jsonObject = JSONObject()
                                    jsonObject.put("job_name", jobText)
                                    jsonObject.put("slave_id", id!!)
                                    val jsonObjectString = jsonObject.toString()

                                    val buuBodyRequest =
                                        jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


                                    val stringInfo =
                                        viewModel.setJob(сache.token!!,buuBodyRequest)
                                            .toString()
                                    Log.e("SET JOB2", stringInfo)

                                }
                                navController.navigate("user_profile",)
                            },
                        ) {
                            Text("Ок")
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun FullScreenDialogRoute(сache: Сache, navController: NavController, viewModel: FellowViewModel ,id:Int?,showDialog:Boolean, hasFetter:Boolean?,setShowDialogRoute: (Boolean)->Unit) {

    var jobText by remember { mutableStateOf("") }


    if (showDialog) {
        Dialog(onDismissRequest = {}) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(400.dp)
                    .height(450.dp)
                    .background(Color.White)
                    .padding(bottom = 7.dp)
            ) {

                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)

                ) {
                    Text(
                        text = "Покупка цепей", fontWeight = FontWeight(600),
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 18.sp,
                    )

                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                    //.padding(start = 5.dp, end = 5.dp, top = 5.dp)

                ) {

                    Column() {


                        Box(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Обычные на 2ч", fontWeight = FontWeight(600),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                            )
                            Button(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                onClick = {
                                    runBlocking {
                                        setShowDialogRoute(false)
                                        runBlocking {

                                            val jsonObject = JSONObject()
                                            jsonObject.put("fetter_type", "common")
                                            jsonObject.put("slave_id", id!!)
                                            val jsonObjectString = jsonObject.toString()

                                            val buuBodyRequest =
                                                jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


                                            val stringInfo =
                                                viewModel.setFetter(сache.token!!,buuBodyRequest)
                                                    .toString()
                                            Log.e("SET COMMON FETTER: ", stringInfo)

                                        }
                                        navController.navigate("user_profile",)
                                    }

                                }) {
                                Text(
                                    text = "Купить",
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Необычные на 4ч", fontWeight = FontWeight(600),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                            )
                            Button(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                onClick = {
                                    setShowDialogRoute(false)
                                    runBlocking {


                                        val jsonObject = JSONObject()
                                        jsonObject.put("fetter_type", "uncommon")
                                        jsonObject.put("slave_id", id!!)
                                        val jsonObjectString = jsonObject.toString()

                                        val buuBodyRequest =
                                            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


                                        val stringInfo =
                                            viewModel.setFetter(сache.token!!, buuBodyRequest)
                                                .toString()
                                        Log.e("SET uncommon FETTER: ", stringInfo)

                                    }
                                    navController.navigate("user_profile",)
                                }) {
                                Text(
                                    text = "Купить",
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Редкие на 6ч", fontWeight = FontWeight(600),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                            )
                            Button(modifier = Modifier
                                .align(Alignment.CenterEnd),
                                onClick = {
                                    setShowDialogRoute(false)
                                    runBlocking {



                                        val jsonObject = JSONObject()
                                        jsonObject.put("fetter_type",  "rare")
                                        jsonObject.put("slave_id", id!!)
                                        val jsonObjectString = jsonObject.toString()

                                        val buuBodyRequest =
                                            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())




                                        val stringInfo =
                                            viewModel.setFetter(сache.token!!,buuBodyRequest)
                                                .toString()
                                        Log.e("SET rare FETTER: ", stringInfo)

                                    }
                                    navController.navigate("user_profile",)
                                }) {
                                Text(
                                    text = "Купить",
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Уникальные на 8ч",
                                fontWeight = FontWeight(600),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                            )
                            Button(modifier = Modifier
                                .align(Alignment.CenterEnd),
                                onClick = {
                                    setShowDialogRoute(false)
                                    runBlocking {






                                        val jsonObject = JSONObject()
                                        jsonObject.put("fetter_type",  "epic")
                                        jsonObject.put("slave_id", id!!)
                                        val jsonObjectString = jsonObject.toString()

                                        val buuBodyRequest =
                                            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())





                                        val stringInfo =
                                            viewModel.setFetter(сache.token!!, buuBodyRequest )
                                                .toString()
                                        Log.e("SET epic FETTER: ", stringInfo)

                                    }
                                    navController.navigate("user_profile",)
                                }) {
                                Text(
                                    text = "Купить",
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Эпические на 12ч",
                                fontWeight = FontWeight(600),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                            )
                            Button(modifier = Modifier
                                .align(Alignment.CenterEnd),
                                onClick = {
                                    setShowDialogRoute(false)
                                    runBlocking {


                                        val jsonObject = JSONObject()
                                        jsonObject.put("fetter_type",  "immortal")
                                        jsonObject.put("slave_id", id!!)
                                        val jsonObjectString = jsonObject.toString()

                                        val buuBodyRequest =
                                            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


                                        val stringInfo =
                                            viewModel.setFetter(сache.token!!, buuBodyRequest )
                                                .toString()
                                        Log.e("SET immortal FETTER: ", stringInfo)
                                    }
                                    navController.navigate("user_profile",)
                                }) {
                                Text(
                                    text = "Купить",
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Легендарные на 24ч",
                                fontWeight = FontWeight(600),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                            )
                            Button(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)

                                ,

                                onClick = {
                                    setShowDialogRoute(false)
                                    runBlocking {


                                        val jsonObject = JSONObject()
                                        jsonObject.put("fetter_type",  "legendary")
                                        jsonObject.put("slave_id", id!!)
                                        val jsonObjectString = jsonObject.toString()

                                        val buuBodyRequest =
                                            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


                                        val stringInfo =
                                            viewModel.setFetter(сache.token!!,buuBodyRequest )
                                                .toString()
                                        Log.e("SET legendary FETTER: ", stringInfo)
                                    }
                                    navController.navigate("user_profile",)
                                }) {
                                Text(
                                    text = "Купить",
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Button(
                                onClick = {
                                    setShowDialogRoute(false)
                                    navController.navigate("user_profile",)
                                },
                            ) {
                                Text("Отмена")
                            }
                        }
                    }
                }
            }
        }
    }
}



