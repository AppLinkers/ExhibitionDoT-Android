package com.exhibitiondot.data.model.dto

data class EventDto(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val date: String,
    val likeCount: Int
)
