package com.example.slave3012314134124123.fellow

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.repository.UserRepository
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
            "AccessToken 9685e571bf1f6aaab7298777c83450a016b7369e6c053a67668e0ed4f8438950de33df6eb5e2fa8afbd04",
            fellowBodyRequest
        )
        return result
    }
}

