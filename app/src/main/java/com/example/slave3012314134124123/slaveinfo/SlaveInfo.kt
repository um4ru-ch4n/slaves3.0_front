package com.example.slave3012314134124123.slaveinfo

import android.util.Log
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
import com.example.slave3012314134124123.data.models.Сache
import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.fellow.FellowViewModel
import com.example.slave3012314134124123.friendslist.MoneyStr
import com.example.slave3012314134124123.util.Resource
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.runBlocking


@Composable
fun SlaveInfoScreen(
    viewModel: FellowViewModel = hiltNavGraphViewModel(),
    idFellow: Int?,
    сache: Сache,
    navController: NavController
) {
    val slaveInfo = produceState<Resource<Fellow>>(initialValue = Resource.Loading()) {
        value = viewModel.loadFellow("THIS TOKEN", idFellow!!)
    }

    val (showJob, setShowDialog) = remember { mutableStateOf(false) }
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
                    .height(100.dp)
                    .background(Color.White)

            ) {
                Row(modifier = Modifier.padding(15.dp)) {
                    Surface(shape = CircleShape) {

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
                                if (сache.user_id == slaveInfo.value.data?.master_id) {
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
                            val stringInfo =
                                viewModel.saleFellow("THIS TOKEN", slaveInfo.value.data!!.id)
                                    .toString()
                            Log.e("BUY", stringInfo)

                        }
                        navController.navigate("user_profile",)
                    }) {
                        Text(text = "Продать")
                    }
                    Button(onClick = {
                        setShowDialog(true)
                    }) {
                        Text(text = "Назначить")
                    }
                //}
//                else {
//                    Button(onClick = {
//
//                        runBlocking {
//                            val stringInfo = viewModel.buyFellow(
//                                "THIS TOKEN",
//                                slaveInfo.value.data!!.id
//                            ).message.toString()
//                            Log.e("BUY", stringInfo)
//                        }
//                        navController.navigate("user_profile",)
//
//                    }) {
//                        Text(text = "Купить")
//                    }
//                }
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
                navController,
                viewModel,
                slaveInfo.value.data?.id,
                showJob,
                setShowDialog
            )
        }
    }
}

@Composable
fun FullScreenDialog(navController: NavController, viewModel: FellowViewModel ,id:Int?,showDialog:Boolean,setShowDialog: (Boolean)->Unit) {

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
                                Log.e("SET JOB1", "work")
                                runBlocking {
                                    val stringInfo =
                                        viewModel.setJob("THIS TOKEN", id!!, jobText)
                                            .toString()
                                    Log.e("SET JOB2", stringInfo)

                                }
                                navController.navigate("user_profile",)
                            },
                        ) {
                            Text("Ок")
                        }
                        Button(
                            onClick = {
                                setShowDialog(false)
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




