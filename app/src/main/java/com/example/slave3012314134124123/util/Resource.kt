package com.example.slave3012314134124123.util

open class Resource<T>(val data: T? = null, var message: String? = null)
{
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String,data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}