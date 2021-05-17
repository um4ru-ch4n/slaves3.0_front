package com.example.slave3012314134124123.navigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.slave3012314134124123.data.models.Сache


@Composable
fun NavigationBarScreen(
    сache: Сache,
    navController: NavController,
    //modifier: Modifier
) {
    Column() {


        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .shadow(4.dp, RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .background(Color.White)
                .height(60.dp)
                .padding(top = 10.dp)
            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Surface(
                    shape = RoundedCornerShape(10.dp),

                    modifier = Modifier
                        //.background(Color.Gray)
                        .height(40.dp)
                        .shadow(15.dp, RoundedCornerShape(5.dp))
                        //.fillMaxWidth()
                        .width(100.dp)
                        .clickable {
                            navController.navigate(
                                "user_profile"
                            )
                        }
                ) {
                    Text(
                        text = "Профиль",
                        //color = Color.Blue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        //.background(Color.Gray)
                        .height(40.dp)
                        .shadow(15.dp, RoundedCornerShape(5.dp))
                        //.fillMaxWidth()
                        .width(100.dp)
                        .clickable {
                            navController.navigate(
                                "friends_list"

                            )
                        }
                ) {
                    Text(
                        text = "Друзья",
                        //color = Color.Blue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 10.dp))
                }
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        //background(Color.Gray)
                        .height(40.dp)
                        .shadow(15.dp, RoundedCornerShape(5.dp))
                        .width(100.dp)
                        .clickable {
                            navController.navigate(
                                "rating"
                            )
                        }
                ) {
                    Text(
                        text = "Рейтинг",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }

            }
        }

    }
}