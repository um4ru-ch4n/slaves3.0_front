package com.example.slave3012314134124123

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class TestTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun first_launch_app() {
        composeTestRule.onNodeWithText("Друзья").assertExists()
        composeTestRule.onNodeWithText("Рейтинг").assertExists()
        composeTestRule.onNodeWithText("Профиль").assertExists()
    }

    @Test
    fun verification_of_authorization() {
        composeTestRule.onNodeWithText("Друзья").assertExists()
        composeTestRule.onNodeWithText("Рейтинг").assertExists()
        composeTestRule.onNodeWithText("Профиль").assertExists()
        composeTestRule.onNodeWithText("Работников 12").assertExists()
        composeTestRule.onNodeWithText("выйти").assertExists()
        composeTestRule.onNodeWithText("Профиль").assertExists()
        composeTestRule.onNodeWithText("Чернышев Влад").assertExists()

    }

    @Test
    fun check_friend_list() {
        composeTestRule.onNodeWithText("Друзья").performClick()
        composeTestRule.onRoot().printToLog("TAG")
        composeTestRule.onNodeWithText("Нуруллина Регина, Босс отсутствует , 1, Стоимость , SM 2 , GM 0, 1").assertExists()
    }


    @Test
    fun checking_other_profiles() {
        composeTestRule.onNodeWithText("Друзья").performClick()
        composeTestRule.onNodeWithText("Нуруллина Регина, Босс отсутствует , 1, Стоимость , SM 2 , GM 0, 1").performGesture { swipeDown() }
        composeTestRule.onNodeWithText("Нуруллина Регина, Босс отсутствует , 1, Стоимость , SM 2 , GM 0, 1").performGesture { swipeUp() }
        composeTestRule.onNodeWithText("Нуруллина Регина, Босс отсутствует , 1, Стоимость , SM 2 , GM 0, 1").performClick()
        composeTestRule.onNodeWithText("Купить").assertExists()
        composeTestRule.onNodeWithText("Рейтинг").performClick()
        composeTestRule.onNodeWithText("1, Oleynikov Alexander, Работников 15").performClick()
        composeTestRule.onNodeWithText("Купить").assertExists()
        composeTestRule.onNodeWithText("Kirillov Kira, lvl 30,   lvl 1, +17706, Нет").assertExists()
        composeTestRule.onNodeWithText("Miroshnik Gosha, lvl 29,   lvl 1, +16056, Нет").assertExists()
    }

//    @Test
//    fun buy_and_sell() {
//        composeTestRule.onNodeWithText("Друзья").performClick()
//        composeTestRule.onNodeWithText("Нуруллина Регина, Босс отсутствует , 1, Стоимость , SM 2 , GM 0, 1").performClick()
//        composeTestRule.onNodeWithText("Купить").performClick()
//    }

}