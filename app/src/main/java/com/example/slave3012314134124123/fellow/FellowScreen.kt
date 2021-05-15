package com.example.slave3012314134124123.fellow

import android.telecom.Call
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.example.slave3012314134124123.data.models.YouId
import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.util.Resource
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.*

@Composable
fun FellowScreen (
    youId :Int,
    viewModel: FellowViewModel = hiltNavGraphViewModel(),
    idFellow: Int?,
    navController: NavController
) {

    val userInfo = produceState<Resource<Fellow>>(initialValue = Resource.Loading()) {
        value = viewModel.loadFellow("THIS TOKEN", idFellow!!)
    }

    Column() {
        Text("Это Профиль Друга")

        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .shadow(4.dp, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White)
        ) {



            Row(modifier = Modifier.padding(15.dp)) {
                
                Column() {


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
                            userInfo.value.data?.let { Text(text = it.fio) }
                        }
                    }
                    

                    
                }
            }


        }

        if(youId==userInfo.value.data?.master_id){
            Log.e("sdf", "YEEEEEEEEEEEEEEEEEEEEEEEES ${youId}  ${userInfo.value.data?.master_id}")

            Button(onClick = {
                runBlocking {
                    val stringInfo = viewModel.saleFellow("THIS TOKEN", userInfo.value.data!!.id).toString()
                    Log.e("BUY", stringInfo)

                }
                navController.navigate("friends_list",)
            }) {
                Text(text = "Продать")
            }

        } else if(youId!=userInfo.value.data?.id) {
            Button(onClick = {

                runBlocking {
                    val stringInfo = viewModel.buyFellow("THIS TOKEN", userInfo.value.data!!.id).message.toString()
                    Log.e("BUY", stringInfo)
                }
                navController.navigate("friends_list",)

            }) {
                Text(text = "Купить")
            }
        }
    }

}


suspend fun BuySlave(id : Int, viewModel: FellowViewModel)
{
    viewModel.buyFellow("daf", id)
}
