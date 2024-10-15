package com.exhibitiondot.domain.model

data class Event(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val date: String,
    val likeCount: Int
)
