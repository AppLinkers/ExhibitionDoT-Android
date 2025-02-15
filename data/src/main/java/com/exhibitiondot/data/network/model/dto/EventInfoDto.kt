package com.exhibitiondot.data.network.model.dto

data class EventInfoDto(
    val name: String,
    val date: String,
    val region: String,
    val categoryList: List<String>,
    val eventTypeList: List<String>,
)
