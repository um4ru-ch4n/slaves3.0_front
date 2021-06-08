package com.example.slave3012314134124123.ratinglist

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
class RatingListViewModel @Inject constructor(
    private val repository: UserRepository
    ): ViewModel() {

    var ratingList = mutableStateOf<List<RatingListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    lateinit var result: Resource<RatingList>

    suspend fun loadRatingListPaginated(token: String) {
        isLoading.value = true

        result =
            repository.getRatingList("AccessToken ${token}")

        if(result.message == null)
            result.message = "Загрузка рейтинга успешна"
        else
            result.message = "Произошла ошибка загрузки рейтинга"



        when (result) {
            is Resource.Success -> {

                val ratingEntries = result.data!!.mapIndexed { index, entry ->
                    val fio = entry.fio
                    val photo = entry.photo
                    val slaves_count = entry.slaves_count
                    val id = entry.id
                    val fetter_type = entry.fetter_type
                    val fetter_time = entry.fetter_time
                    val has_fetter = entry.has_fetter
                    val fetter_duration = entry.fetter_duration
                    RatingListEntry(
                        fio,
                        photo,
                        slaves_count,
                        id,
                        has_fetter,
                        fetter_type,
                        fetter_time,
                        fetter_duration
                    )
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