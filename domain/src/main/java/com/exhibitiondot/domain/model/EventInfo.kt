package com.exhibitiondot.domain.model

data class EventInfo(
    val name: String,
    val date: String,
    val region: Region,
    val categoryList: List<Category>,
    val eventTypeList: List<EventType>,
)
