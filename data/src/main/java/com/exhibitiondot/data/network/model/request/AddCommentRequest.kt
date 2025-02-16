package com.exhibitiondot.data.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AddCommentRequest(
    val content: String
)
