package com.exhibitiondot.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val userId: Long
)
