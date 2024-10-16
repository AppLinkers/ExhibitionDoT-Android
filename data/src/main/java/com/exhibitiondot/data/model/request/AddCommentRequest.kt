package com.exhibitiondot.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AddCommentRequest(
    val content: String
)
