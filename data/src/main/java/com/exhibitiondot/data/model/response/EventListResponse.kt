package com.exhibitiondot.data.model.response

import com.exhibitiondot.data.model.dto.EventDto
import kotlinx.serialization.Serializable

@Serializable
data class EventListResponse(
    val contents: List<EventDto>,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPage: Int,
    val first: Boolean,
    val last: Boolean
)
