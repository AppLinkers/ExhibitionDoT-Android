package com.exhibitiondot.data.network.model.response

import com.exhibitiondot.data.network.model.dto.EventDto
import kotlinx.serialization.Serializable

@Serializable
data class EventListResponse(
    val contents: List<EventDto>,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val first: Boolean,
    val last: Boolean
)
