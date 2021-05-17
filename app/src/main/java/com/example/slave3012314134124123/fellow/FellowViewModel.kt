package com.example.slave3012314134124123.fellow

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Constants.TOKEN
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject



@HiltViewModel
class FellowViewModel @Inject constructor(
    private  val repository: UserRepository
)  : ViewModel() {


    suspend fun loadFellow(token: String, id: Int):Resource<Fellow> {

        val jsonObject = JSONObject()
        jsonObject.put("user_id", id)
        val jsonObjectString = jsonObject.toString()

        val fellowBodyRequest =
            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val result = repository.postFellow(
            "AccessToken ${TOKEN}",
            fellowBodyRequest
        )
        Log.e("LOAD-FELLOW", result.message.toString())
        return result
    }

    suspend fun buyFellow(token: String, id: Int):Resource<String>{
        val jsonObject = JSONObject()
        jsonObject.put("slave_id", id)
        val jsonObjectString = jsonObject.toString()

        val buuBodyRequest =
            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val result = repository.postBuy(
            "AccessToken ${TOKEN}",
            buuBodyRequest
        )
        Log.e("BUY", result.message.toString())
        return result
    }

    suspend fun saleFellow(token: String, id: Int):Resource<String>{
        val jsonObject = JSONObject()
        jsonObject.put("slave_id", id)
        val jsonObjectString = jsonObject.toString()

        val buuBodyRequest =
            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val result = repository.postSale(
            "AccessToken ${TOKEN}",
            buuBodyRequest
        )
        Log.e("SALE", result.message.toString())
        return result
    }

    suspend fun setJob(token: String, id: Int, job:String):Resource<String>{
        val jsonObject = JSONObject()
        jsonObject.put("job_name", job)
        jsonObject.put("slave_id", id)
        val jsonObjectString = jsonObject.toString()

        val buuBodyRequest =
            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val result = repository.postSetJob(
            "AccessToken ${TOKEN}",
            buuBodyRequest
        )
        Log.e("SET-JOB", result.message.toString())
        return result
    }


}

