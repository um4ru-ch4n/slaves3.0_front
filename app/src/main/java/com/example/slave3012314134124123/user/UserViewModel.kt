package com.example.slave3012314134124123.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slave3012314134124123.data.models.UserEntry
import com.example.slave3012314134124123.data.remote.responses.User
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Constants
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private  val repository: UserRepository
) : ViewModel(){



    suspend fun loadUser(token:String):Resource<User>{
        return repository.getUser("AccessToken ${Constants.TOKEN}")

    }

}