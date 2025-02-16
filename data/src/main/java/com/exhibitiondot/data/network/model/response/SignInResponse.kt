package com.exhibitiondot.data.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val userId: Long
)
