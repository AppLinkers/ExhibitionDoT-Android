package com.exhibitiondot.presentation.model

data class EventUiModel(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val date: String,
    val likeCount: Int
)
