package com.exhibitiondot.presentation.mapper

import com.exhibitiondot.domain.exception.NetworkFailException

fun Throwable.getMessage(defaultMsg: String): String {
    return when (this) {
        is NetworkFailException -> error
        else -> defaultMsg
    }
}