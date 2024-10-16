package com.exhibitiondot.data.datasource

import com.exhibitiondot.data.model.dto.EventDetailDto
import com.exhibitiondot.data.model.dto.EventDto
import kotlinx.coroutines.flow.Flow

interface EventDataSource {
    fun getEventList(
        page: Int? = null,
        size: Int? = null,
        region: String? = null,
        categoryList: List<String>? = null,
        eventTypeList: List<String>? = null,
        query: String?
    ): Flow<List<EventDto>>

    fun getEventDetail(eventId: Long): Flow<EventDetailDto?>
}