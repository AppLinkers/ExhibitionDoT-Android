package com.exhibitiondot.data.datasource.event

import androidx.paging.PagingData
import com.exhibitiondot.data.model.dto.EventDetailDto
import com.exhibitiondot.data.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import com.exhibitiondot.domain.model.EventParams
import kotlinx.coroutines.flow.Flow

interface EventDataSource {
    fun getEventList(params: EventParams): Flow<PagingData<EventDto>>

    suspend fun getEventDetail(eventId: Long): NetworkState<EventDetailDto>

    suspend fun toggleEventLike(eventId: Long): NetworkState<Unit>
}