package com.exhibitiondot.data.network.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val id: Long,
    @SerialName("authorNickName")
    val authorNickname: String,
    val content: String,
    val createdAt: String
)
