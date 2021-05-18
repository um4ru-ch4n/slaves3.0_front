package com.example.slave3012314134124123.slaveslist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slave3012314134124123.data.models.SlavesListEntry
import com.example.slave3012314134124123.data.remote.responses.FriendsList
import com.example.slave3012314134124123.data.remote.responses.SlavesList
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SlavesListViewModel2  @Inject constructor(
    private val repository: UserRepository
): ViewModel() {


    suspend fun loadSlavesPaginated(token: String): Resource<SlavesList> {

        Log.e("VM-FL", "token ${token}")


        val result =
            repository.getSlavesList("AccessToken ${token}")

        //Log.e("VM-FL", "INFO  ${result.message.toString()}")

        return result
    }


}