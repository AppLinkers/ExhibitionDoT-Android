package com.exhibitiondot.data.network.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventInfoDto(
    val name: String,
    val date: String,
    val region: String,
    val categoryList: List<String>,
    val eventTypeList: List<String>,
)
