package com.exhibitiondot.data.datasource

import androidx.paging.PagingData
import com.exhibitiondot.data.model.dto.EventDetailDto
import com.exhibitiondot.data.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import kotlinx.coroutines.flow.Flow

interface EventDataSource {
    fun getEventList(
        region: String?,
        categoryList: List<String>,
        eventTypeList: List<String>,
        query: String
    ): Flow<PagingData<EventDto>>

    fun getEventDetail(eventId: Long): Flow<EventDetailDto?>

    suspend fun toggleEventLike(eventId: Long): NetworkState<Unit>
}