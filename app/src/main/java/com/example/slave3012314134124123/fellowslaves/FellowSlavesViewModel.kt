package com.example.slave3012314134124123.fellowslaves

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slave3012314134124123.data.models.FellowSlavesEntry
import com.example.slave3012314134124123.data.models.SlavesListEntry
import com.example.slave3012314134124123.data.remote.responses.FellowSlaves
import com.example.slave3012314134124123.data.remote.responses.SlavesList
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class FellowSlavesViewModel  @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    var slavesList = mutableStateOf<List<FellowSlavesEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    lateinit var result: Resource<FellowSlaves>

    var token2 = mutableStateOf<String>("")
    var user_id = mutableStateOf(0)



    fun loadFellowSlavesPaginated() {
        viewModelScope.launch {
            isLoading.value = true

            val jsonObject = JSONObject()
            jsonObject.put("user_id", user_id.value)
            val jsonObjectString = jsonObject.toString()

            val buuBodyRequest =
                jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            Log.e("FELLOW-SLAVE-LIST ID", "ID: ${user_id.value}")
            Log.e("FELLOW-SLAVE-LIST TOKEN", "T: ${token2.value}")

            result =
                repository.postGetFellowSlaves("AccessToken ${token2.value}",buuBodyRequest)

            Log.e("FELLOW-SLAVE-LIST SIZE", "size ${result.data?.size}")

            Log.e("FELLOW-SLAVE-LIST", result.message.toString())


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
                        val fetter_type = entry.fetter_type
                        val fetter_time = entry.fetter_time
                        val has_fetter = entry.has_fetter
                        val fetter_duration = entry.fetter_duration

                        FellowSlavesEntry(fio, photo, profit, job_name, slaveLvl, defLvl, id,fetter_type,fetter_time,has_fetter,fetter_duration)
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