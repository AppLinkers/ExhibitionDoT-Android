package com.exhibitiondot.data.model.dto

data class CommentDto(
    val id: Long,
    val authorNickname: String,
    val content: String,
    val createdAt: String
)
