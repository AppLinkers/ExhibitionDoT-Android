package com.exhibitiondot.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.exhibitiondot.data.datasource.event.EventDataSource
import com.exhibitiondot.data.mapper.toDomain
import com.exhibitiondot.data.mapper.toDto
import com.exhibitiondot.data.network.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
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
        val response = eventDataSource.getEventDetail(eventId)
        return when (response) {
            is NetworkState.Success -> Result.success(response.data.toDomain())
            else -> Result.failure(IllegalStateException())
        }
    }

    override suspend fun toggleEventLike(eventId: Long): Result<Unit> {
        val response = eventDataSource.toggleEventLike(eventId)
        return when (response) {
            is NetworkState.Success -> Result.success(response.data)
            else -> Result.failure(IllegalStateException())
        }
    }

    override suspend fun addEvent(file: File, eventInfo: EventInfo): Result<Unit> {
        val response = eventDataSource.addEvent(file, eventInfo.toDto())
        return when (response) {
            is NetworkState.Success -> Result.success(response.data)
            else -> Result.failure(IllegalStateException())
        }
    }

    override suspend fun updateEvent(
        file: File?,
        eventInfo: EventInfo?,
        eventId: Long
    ): Result<Unit> {
        val response = eventDataSource.updateEvent(file, eventInfo?.toDto(), eventId)
        return when (response) {
            is NetworkState.Success -> Result.success(response.data)
            else -> Result.failure(IllegalStateException())
        }
    }

    override suspend fun deleteEvent(eventId: Long): Result<Unit> {
        val response = eventDataSource.deleteEvent(eventId)
        return when (response) {
            is NetworkState.Success -> Result.success(response.data)
            else -> Result.failure(IllegalStateException())
        }
    }
}