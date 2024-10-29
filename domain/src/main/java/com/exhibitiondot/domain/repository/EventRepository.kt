package com.exhibitiondot.domain.repository

import androidx.paging.PagingData
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.model.EventParams
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEventList(params: EventParams): Flow<PagingData<Event>>

    fun getEventDetail(eventId: Long): Flow<EventDetail>

    suspend fun toggleEventLike(eventId: Long): Result<Unit>
}