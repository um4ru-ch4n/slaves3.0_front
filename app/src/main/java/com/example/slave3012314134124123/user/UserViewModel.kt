package com.example.slave3012314134124123.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slave3012314134124123.data.models.UserEntry
import com.example.slave3012314134124123.data.remote.responses.User
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private  val repository: UserRepository
) : ViewModel(){



    suspend fun loadUser(token:String):Resource<User>{
        return repository.getUser("AccessToken 9685e571bf1f6aaab7298777c83450a016b7369e6c053a67668e0ed4f8438950de33df6eb5e2fa8afbd04")

    }

}