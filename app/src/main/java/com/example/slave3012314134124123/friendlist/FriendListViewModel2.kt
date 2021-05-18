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
import com.example.slave3012314134124123.data.remote.responses.FriendsList
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FriendsListViewModel2 @Inject constructor(
    private val repository: UserRepository,
): ViewModel() {

    suspend fun loadFriendsPaginated(token: String): Resource<FriendsList> {

        Log.e("VM-FL", "token ${token}")


        val result =
            repository.getFriendsList("AccessToken ${token}")

        Log.e("VM-FL", "INFO  ${result.message.toString()}")

        return result
    }


}