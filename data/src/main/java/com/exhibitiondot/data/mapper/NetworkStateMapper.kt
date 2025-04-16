package com.exhibitiondot.data.mapper

import com.exhibitiondot.data.network.NetworkState
import com.exhibitiondot.domain.exception.NetworkFailException

fun <T, R> NetworkState<T>.toResult(map: (T) -> R): Result<R> {
    return when (this) {
        is NetworkState.Success -> Result.success(map(data))
        is NetworkState.Failure -> Result.failure(
            NetworkFailException(errorResponse.errorCode, errorResponse.message)
        )
        is NetworkState.NetworkError -> Result.failure(error)
        is NetworkState.UnknownError -> Result.failure(t)
    }
}