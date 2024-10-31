package com.exhibitiondot.presentation.model

data class CommentUiModel(
    val id: Long,
    val nickname: String,
    val content: String,
    val createdAt: String
)