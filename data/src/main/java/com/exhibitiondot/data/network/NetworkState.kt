package com.exhibitiondot.data.network

import com.exhibitiondot.data.network.model.response.ErrorResponse
import java.io.IOException

sealed class NetworkState<out T> {
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Failure(val errorResponse: ErrorResponse) : NetworkState<Nothing>()
    data class NetworkError(val error: IOException) : NetworkState<Nothing>()
    data class UnknownError(val t: Throwable) : NetworkState<Nothing>()
}