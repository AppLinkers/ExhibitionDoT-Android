package com.exhibitiondot.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val email: String
)
