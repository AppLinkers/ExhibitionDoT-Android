package com.exhibitiondot.data.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val errorCode: String,
    val message: String,
)
