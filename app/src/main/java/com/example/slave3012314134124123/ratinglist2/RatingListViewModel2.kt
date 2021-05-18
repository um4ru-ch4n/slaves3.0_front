package com.example.slave3012314134124123.ratinglist2

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slave3012314134124123.data.models.FriendsListEntry
import com.example.slave3012314134124123.data.models.RatingListEntry
import com.example.slave3012314134124123.data.models.Сache
import com.example.slave3012314134124123.data.remote.responses.RatingList
import com.example.slave3012314134124123.data.remote.responses.SlavesList
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Constants
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingListViewModel2 @Inject constructor(
    private val repository: UserRepository
): ViewModel() {


    suspend fun loadRatingListPaginated(token: String): Resource<RatingList> {

        Log.e("VM-FL", "token ${token}")


        val result =
            repository.getRatingList("AccessToken ${token}")

        //Log.e("VM-FL", "INFO  ${result.message.toString()}")

        return result
    }
}
