package com.exhibitiondot.data.datasource.event

import androidx.paging.PagingData
import com.exhibitiondot.data.network.model.dto.EventDetailDto
import com.exhibitiondot.data.network.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import com.exhibitiondot.data.network.model.dto.EventInfoDto
import com.exhibitiondot.domain.model.EventParams
import kotlinx.coroutines.flow.Flow
import java.io.File

interface EventDataSource {
    fun getEventList(params: EventParams): Flow<PagingData<EventDto>>

    suspend fun getEventDetail(eventId: Long): NetworkState<EventDetailDto>

    suspend fun toggleEventLike(eventId: Long): NetworkState<Unit>

    suspend fun addEvent(file: File, eventInfo: EventInfoDto) : NetworkState<Unit>

    suspend fun updateEvent(
        file: File?,
        eventInfo: EventInfoDto?,
        eventId: Long
    ) : NetworkState<Unit>

    suspend fun deleteEvent(eventId: Long) : NetworkState<Unit>
}