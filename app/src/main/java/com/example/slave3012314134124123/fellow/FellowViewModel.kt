package com.example.slave3012314134124123.fellow

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject



@HiltViewModel
class FellowViewModel @Inject constructor(
    private  val repository: UserRepository
)  : ViewModel() {


    suspend fun loadFellow(token: String, fellowBodyRequest: RequestBody):Resource<Fellow> {

        val result = repository.postFellow(
            "AccessToken ${token}",
            fellowBodyRequest
        )

        if(result.message == null)
            result.message = "Загрузка профиля пользователя успешна"
        else
            result.message = "Произошла ошибка загрузки профиля пользователя"


        //Log.e("LOAD-FELLOW", result.message.toString())
        return result
    }

    suspend fun buyFellow(token: String, buuBodyRequest: RequestBody):Resource<String>{
        val result = repository.postBuy(
            "AccessToken ${token}",
            buuBodyRequest
        )

        if(result.message == null)
            result.message = "Покупка прошла успешно"
        else
            result.message = "В покупке отказано"

        //Log.e("BUY", result.message.toString())
        return result
    }

    suspend fun saleFellow(token: String, buuBodyRequest: RequestBody):Resource<String>{


        val result = repository.postSale(
            "AccessToken ${token}",
            buuBodyRequest
        )
        if(result.message == null)
            result.message = "Продажа прошла успешно"
        else
            result.message = "Ошибка продажи"

        //Log.e("SALE", result.message.toString())
        return result
    }

    suspend fun setJob(token: String, buuBodyRequest: RequestBody):Resource<String>{
        val result = repository.postSetJob(
            "AccessToken ${token}",
            buuBodyRequest
        )
        if(result.message == null)
            result.message = "Работа назначена"
        else
            result.message = "Ошибка назначения работы"
        //Log.e("SET-JOB", result.message.toString())
        return result
    }



    suspend fun setFetter(token: String, buuBodyRequest:RequestBody):Resource<String>{


        val result = repository. postSetFetter(
            "AccessToken ${token}",
            buuBodyRequest
        )
        if(result.message == null)
            result.message = "Цепи установлены"
        else
            result.message = "Ошибка уставноки цепей"
        //Log.e("SET-FETTER", "I: ${result.message.toString()}")
        return result
    }


}

