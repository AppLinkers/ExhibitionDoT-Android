package com.exhibitiondot.domain.model

data class Comment(
    val id: Long,
    val authorNickname: String,
    val content: String,
    val createdAt: String
)
