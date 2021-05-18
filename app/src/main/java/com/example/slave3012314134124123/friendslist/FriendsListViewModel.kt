package com.example.slave3012314134124123.friendslist

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slave3012314134124123.MainActivity
import com.example.slave3012314134124123.data.models.FriendsListEntry
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FriendsListViewModel @Inject constructor(
    private val repository: UserRepository,
): ViewModel() {


//    var friendsList = mutableStateOf<List<FriendsListEntry>>(listOf())
//    var loadError = mutableStateOf("")
//    var isLoading = mutableStateOf(false)
//    var token = mutableStateOf("")
//
//
//
//
//    suspend fun loadFriendsPaginated(token:String):Resource<FriendsList> {
//
//            Log.e("VM-FL", "token ${token}")
//
//
//            val result =
//                repository.getFriendsList("AccessToken ${token}")
//
//            Log.e("VM-FL", "INFO  ${result.message.toString()}")
//
//        return result
//    }
//
//









    var friendsList = mutableStateOf<List<FriendsListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var token2 = mutableStateOf<String>("")

    init {
        loadFriendsPaginated( "NULL")
    }


    fun loadFriendsPaginated(token:String) {

        viewModelScope.launch {





            //prefs = mainActivity.getSharedPreferences("token", Context.MODE_PRIVATE)
            //val token2 = sdf.getString("token", "")
            //Log.e("VM-FL", "token ${token2}")

            isLoading.value = true
            var result =
                repository.getFriendsList("AccessToken ${token2.value}")
            result =
                repository.getFriendsList("AccessToken ${token2.value}")
           // Log.e("FRIEND-LIST", result.message.toString())

            when (result) {
                is Resource.Success -> {

                    val friendsEntries = result.data!!.mapIndexed { index, entry ->
                        val fio = entry.fio
                        val photo = entry.photo
                        val masterFio = entry.master_fio
                        val slaveLvl = entry.slave_level
                        val defLvl = entry.defender_level
                        val silver = entry.purchase_price_sm
                        val gold = entry.purchase_price_gm
                        val id = entry.id
                        val idMaster = entry.master_id
                        FriendsListEntry(fio, photo, masterFio, slaveLvl,defLvl, silver, gold, id,idMaster)
                    }
                    loadError.value = ""
                    isLoading.value = false
                    friendsList.value += friendsEntries

                }
               is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
           }
        }
    }
}