package com.example.slave3012314134124123.data.remote.responses

data class FriendsListItem(
    val defender_level: Int,
    val fetter_duration: Int,
    val fetter_time: String,
    val fetter_type: String,
    val fio: String,
    val has_fetter: Boolean,
    val id: Int,
    val master_fio: String,
    val master_id: Int,
    val photo: String,
    val purchase_price_gm: Int,
    val purchase_price_sm: Int,
    val slave_level: Int
)