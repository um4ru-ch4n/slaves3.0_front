package com.example.slave3012314134124123.ratinglist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slave3012314134124123.data.models.FriendsListEntry
import com.example.slave3012314134124123.data.models.RatingListEntry
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Constants
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingListViewModel @Inject constructor(

    private val repository: UserRepository
    ): ViewModel() {

        var ratingList = mutableStateOf<List<RatingListEntry>>(listOf())
        var loadError = mutableStateOf("")
        var isLoading = mutableStateOf(false)

        init {
            loadRatingListPaginated()
        }


        fun loadRatingListPaginated() {
            viewModelScope.launch {
                isLoading.value = true
                val result =
                    repository.getRatingList("AccessToken ${Constants.TOKEN}")
                Log.e("VM-RARING", result.message.toString())
                when (result) {
                    is Resource.Success -> {

                        val ratingEntries = result.data!!.mapIndexed { index, entry ->
                            val fio = entry.fio
                            val photo = entry.photo
                            val slaves_count = entry.slaves_count
                            val id = entry.id
                            RatingListEntry(fio,photo,slaves_count,id)
                        }
                        loadError.value = ""
                        isLoading.value = false
                        ratingList.value += ratingEntries

                    }
                    is Resource.Error -> {
                        loadError.value = result.message!!
                        isLoading.value = false
                    }
                }
            }
        }
    }