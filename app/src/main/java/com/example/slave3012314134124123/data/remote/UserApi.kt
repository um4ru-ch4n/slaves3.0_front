package com.example.slave3012314134124123.data.remote

import com.example.slave3012314134124123.data.remote.responses.*
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

    @GET("fellow/rating/slavescount")
    suspend fun getRatingList(
        @Header("Authorization") authHeader : String
    ): RatingList

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

    @POST("user/setjobname")
    suspend fun postSetJob(
        @Header("Authorization") authHeader : String,
        @Body requestBody: RequestBody
    ): String

    @POST("user/buyfetter")
    suspend fun postSetFetter(
        @Header("Authorization") authHeader : String,
        @Body requestBody: RequestBody
    ): String

    @POST("fellow/slaves")
    suspend fun postGetFellowSlaves(
        @Header("Authorization") authHeader : String,
        @Body requestBody: RequestBody
    ): FellowSlaves


}