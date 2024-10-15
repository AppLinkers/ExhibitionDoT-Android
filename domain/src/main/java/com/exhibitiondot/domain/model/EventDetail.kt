package com.exhibitiondot.domain.model

data class EventDetail(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val region: Region,
    val categoryList: List<Category>,
    val eventTypeList: List<EventType>,
    val date: String,
    val likeCount: Int,
    val isLike: Boolean,
    val owner: Boolean,
    val createdAt: String
)
