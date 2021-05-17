package com.example.slave3012314134124123.fellow

import android.util.Log
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
import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.friendslist.MoneyStr
import com.example.slave3012314134124123.slaveinfo.FullScreenDialog
import com.example.slave3012314134124123.util.Resource
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.*

@Composable
fun FellowScreen (
    path: String,
    сache: Сache,
    viewModel: FellowViewModel = hiltNavGraphViewModel(),
    idFellow: Int?,
    navController: NavController
) {

    val userInfo = produceState<Resource<Fellow>>(initialValue = Resource.Loading()) {
        value = viewModel.loadFellow("THIS TOKEN", idFellow!!)
    }
    val (showJob, setShowDialog) = remember { mutableStateOf(false) }

    Log.e("FELLOW-SCREEN", "MASTER: ${сache.master_id} YOU: ${сache.user_id}" )

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
                                .data(userInfo.value.data?.photo)
                                .target()
                                .build(),
                            contentDescription = userInfo.value.data?.fio,
                            fadeIn = true,
                            modifier = Modifier
                                .size(70.dp)
                        )

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
                                if (сache.user_id == сache.master_id) {
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
                                        text = "Босс ${if(сache.master_fio!="") сache.master_fio else "отсутствует"}",
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
                if (сache.user_id == сache.master_id && сache.user_id != userInfo.value.data?.id) {
                    Button(onClick = {
                        runBlocking {
                            val stringInfo =
                                viewModel.saleFellow("THIS TOKEN", userInfo.value.data!!.id)
                                    .toString()

                        }
                        navController.navigate(path,)
                    }) {
                        Text(text = "Продать")
                    }
                    Button(onClick = {
                        setShowDialog(true)
                    }) {
                        Text(text = "Назначить")
                    }
                } else if(сache.user_id != userInfo.value.data?.id){
                    Button(onClick = {

                        runBlocking {
                            val stringInfo = viewModel.buyFellow(
                                "THIS TOKEN",
                                userInfo.value.data!!.id
                            ).message.toString()
                        }
                        navController.navigate(path,)

                    }) {
                        Text(text = "Купить")
                    }
                }
            }

            FullScreenDialog(
                navController,
                viewModel,
                userInfo.value.data?.id,
                showJob,
                setShowDialog
            )

        }
    }

}


