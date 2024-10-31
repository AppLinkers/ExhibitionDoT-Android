package com.exhibitiondot.domain.model

data class EventParams(
    val region: Region?,
    val categoryList: List<Category>,
    val eventTypeList: List<EventType>,
    val query: String
)
