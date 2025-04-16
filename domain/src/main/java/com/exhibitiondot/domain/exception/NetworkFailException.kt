package com.exhibitiondot.domain.exception

data class NetworkFailException(val code: String, val error: String) : Exception(error)