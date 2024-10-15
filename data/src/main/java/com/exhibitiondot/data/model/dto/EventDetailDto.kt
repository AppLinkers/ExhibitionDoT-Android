package com.exhibitiondot.data.model.dto

data class EventDetailDto(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val region: String,
    val categoryList: List<String>,
    val eventTypeList: List<String>,
    val date: String,
    val likeCount: Int,
    val isLike: Boolean,
    val owner: Boolean,
    val createdAt: String
)
