package com.exhibitiondot.domain.exception

data class NetworkFailException(val code: Int, val error: String?) : Exception(error)