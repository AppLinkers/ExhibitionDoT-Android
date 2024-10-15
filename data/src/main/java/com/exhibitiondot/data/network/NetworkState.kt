package com.exhibitiondot.data.network

import java.io.IOException

sealed class NetworkState<out T> {
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Failure(val code: Int, val error: String?) : NetworkState<Nothing>()
    data class NetworkError(val error: IOException) : NetworkState<Nothing>()
    data class UnknownError(val t: Throwable?, val errorState: String) : NetworkState<Nothing>()
}