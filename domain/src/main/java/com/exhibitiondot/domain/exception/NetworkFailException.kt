package com.exhibitiondot.domain.exception

class NetworkFailException(code: Int, error: String?) : Exception(error)