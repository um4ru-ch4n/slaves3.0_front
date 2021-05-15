package com.example.slave3012314134124123.repository

import com.example.slave3012314134124123.data.models.FellowBodyRequest
import com.example.slave3012314134124123.data.remote.UserApi
import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.data.remote.responses.FriendsList
import com.example.slave3012314134124123.data.remote.responses.SlavesList
import com.example.slave3012314134124123.data.remote.responses.User
import com.example.slave3012314134124123.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.RequestBody
import javax.inject.Inject

@ActivityScoped
class UserRepository @Inject constructor(
    private val api: UserApi
) {

    suspend fun getFriendsList(authHeader: String): Resource<FriendsList> {
        val response = try {
            api.getFriendsList(authHeader)
        } catch(e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }


    suspend fun getUser(authHeader: String): Resource<User> {
        val response = try {
            api.getUser(authHeader)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)

    }

    suspend fun getSlavesList(authHeader: String): Resource<SlavesList> {
        val response = try {
            api.getSlavesList(authHeader)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)

    }

    suspend fun postFellow(
        authHeader: String,
        requestBody: RequestBody
    ): Resource<Fellow>{
        val response = try {
            api.postFellow(authHeader, requestBody)
        } catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

}