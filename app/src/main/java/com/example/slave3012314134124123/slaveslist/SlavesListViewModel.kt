package com.example.slave3012314134124123.slaveslist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slave3012314134124123.data.models.SlavesListEntry
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SlavesListViewModel  @Inject constructor(
    private val repository: UserRepository
): ViewModel() {


    var slavesList = mutableStateOf<List<SlavesListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

//    init {
//        loadSlavesPaginated(token)
//    }


    fun loadSlavesPaginated(token:String) {
        viewModelScope.launch {
            isLoading.value = true
            val result =
                repository.getSlavesList("AccessToken ${token}")
            Log.e("SLAVE-LIST", result.message.toString())
            when (result) {
                is Resource.Success -> {

                    val slavesEntries = result.data!!.mapIndexed { index, entry ->
                        val fio = entry.fio
                        val photo = entry.photo
                        val profit = entry.profit
                        val job_name = entry.job_name
                        val slaveLvl = entry.slave_level
                        val defLvl = entry.defender_level
                        val id = entry.id

                        SlavesListEntry(fio, photo, profit, job_name, slaveLvl,defLvl,id)
                    }
                    loadError.value = ""
                    isLoading.value = false
                    slavesList.value += slavesEntries

                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}