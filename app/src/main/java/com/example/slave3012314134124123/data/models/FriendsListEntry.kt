package com.example.slave3012314134124123.data.models

data class FriendsListEntry(

    val fio: String,
    val photo: String,
    val masterFio: String,
    val slaveLvl: Int,
    val defLvl: Int,
    val priceSilver: Int,
    val priceGold: Int,
    val id: Int,
    val master_Id: Int,
    val has_fetter: Boolean,
    val fetter_type: String,
    val fetter_time: String,
    val fetter_duration: Int,

)
