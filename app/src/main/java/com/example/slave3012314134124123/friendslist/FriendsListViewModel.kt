package com.example.slave3012314134124123.friendslist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slave3012314134124123.data.models.FriendsListEntry
import com.example.slave3012314134124123.data.remote.UserApi
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class FriendsListViewModel @Inject constructor(

    private val repository: UserRepository
): ViewModel() {


    var friendsList = mutableStateOf<List<FriendsListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        loadFriendsPaginated()
    }


    fun loadFriendsPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result =
                repository.getFriendsList("AccessToken 9685e571bf1f6aaab7298777c83450a016b7369e6c053a67668e0ed4f8438950de33df6eb5e2fa8afbd04")
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
                        FriendsListEntry(fio, photo, masterFio, slaveLvl,defLvl, silver, gold, id)
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