package com.exhibitiondot.domain.model

data class UpdateUserInfo(
    val nickname: String,
    val region: Region,
    val categoryList: List<Category>,
    val eventTypeList: List<EventType>
)
