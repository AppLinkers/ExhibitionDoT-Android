package com.exhibitiondot.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val date: String,
    val likeCount: Int
)
