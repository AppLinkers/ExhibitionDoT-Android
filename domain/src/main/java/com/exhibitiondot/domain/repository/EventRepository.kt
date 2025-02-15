package com.exhibitiondot.domain.repository

import androidx.paging.PagingData
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.model.EventInfo
import com.exhibitiondot.domain.model.EventParams
import kotlinx.coroutines.flow.Flow
import java.io.File

interface EventRepository {
    fun getEventList(params: EventParams): Flow<PagingData<Event>>

    suspend fun getEventDetail(eventId: Long): Result<EventDetail>

    suspend fun toggleEventLike(eventId: Long): Result<Unit>

    suspend fun addEvent(file: File, eventInfo: EventInfo): Result<Unit>

    suspend fun updateEvent(
        file: File?,
        eventInfo: EventInfo?,
        eventId: Long
    ): Result<Unit>

    suspend fun deleteEvent(eventId: Long): Result<Unit>
}