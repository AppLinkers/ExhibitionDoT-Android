package com.exhibitiondot.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.exhibitiondot.data.datasource.event.EventDataSource
import com.exhibitiondot.data.mapper.toDomain
import com.exhibitiondot.data.mapper.toDto
import com.exhibitiondot.data.mapper.toResult
import com.exhibitiondot.data.network.model.dto.EventDto
import com.exhibitiondot.data.network.model.dto.EventDetailDto
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.model.EventInfo
import com.exhibitiondot.domain.model.EventParams
import com.exhibitiondot.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDataSource: EventDataSource
) : EventRepository {
    override fun getEventList(params: EventParams): Flow<PagingData<Event>> =
        eventDataSource
            .getEventList(params)
            .map { pagingData -> pagingData.map(EventDto::toDomain) }

    override suspend fun getEventDetail(eventId: Long): Result<EventDetail> {
        return eventDataSource.getEventDetail(eventId).toResult(EventDetailDto::toDomain)
    }

    override suspend fun toggleEventLike(eventId: Long): Result<Unit> {
        return eventDataSource.toggleEventLike(eventId).toResult {  }
    }

    override suspend fun addEvent(file: File, eventInfo: EventInfo): Result<Unit> {
        return eventDataSource.addEvent(file, eventInfo.toDto()).toResult {  }
    }

    override suspend fun updateEvent(
        file: File?,
        eventInfo: EventInfo,
        eventId: Long
    ): Result<Unit> {
        return eventDataSource.updateEvent(file, eventInfo.toDto(), eventId).toResult {  }
    }

    override suspend fun deleteEvent(eventId: Long): Result<Unit> {
        return eventDataSource.deleteEvent(eventId).toResult {  }
    }
}