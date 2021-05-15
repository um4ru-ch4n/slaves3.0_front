package com.example.slave3012314134124123.data.remote

import com.example.slave3012314134124123.data.remote.responses.Fellow
import com.example.slave3012314134124123.data.remote.responses.FriendsList
import com.example.slave3012314134124123.data.remote.responses.SlavesList
import com.example.slave3012314134124123.data.remote.responses.User
import okhttp3.RequestBody
import retrofit2.http.*


interface UserApi {

    @GET("user")
    suspend fun getUser(
        @Header("Authorization") authHeader : String
    ) : User

    @GET("fellow/friends")
    suspend fun getFriendsList(
        @Header("Authorization") authHeader : String

    ): FriendsList

    @GET("user/slaves")
    suspend fun getSlavesList(
        @Header("Authorization") authHeader : String
    ): SlavesList

    @POST("fellow")
    suspend fun postFellow(
        @Header("Authorization") authHeader : String,
        @Body requestBody: RequestBody
    ): Fellow

    @POST("user/saleslave")
    suspend fun postSale(
        @Header("Authorization") authHeader : String,
        @Body requestBody: RequestBody
    ) : String

    @POST("user/buyslave")
    suspend fun postBuy(
        @Header("Authorization") authHeader : String,
        @Body requestBody: RequestBody
    ): String
}