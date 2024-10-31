package com.exhibitiondot.presentation.model

data class EventDetailUiModel(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val region: String,
    val categoryTags: String,
    val eventTypeTags: String,
    val date: String,
    val createdAt: String,
    val likeCount: Int,
    val isLike: Boolean,
    val owner: Boolean,
)
