package com.exhibitiondot.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.exhibitiondot.data.datasource.EventDataSource
import com.exhibitiondot.data.mapper.toDomain
import com.exhibitiondot.data.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.model.EventParams
import com.exhibitiondot.domain.repository.EventRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDataSource: EventDataSource
) : EventRepository {
    override fun getEventList(params: EventParams): Flow<PagingData<Event>> =
        eventDataSource.getEventList(
            region = params.region?.key ,
            categoryList = params.categoryList.map { it.key },
            eventTypeList = params.eventTypeList.map { it.key },
            query = params.query
        ).map { pagingData -> pagingData.map(EventDto::toDomain) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getEventDetail(eventId: Long): Flow<EventDetail> =
        eventDataSource.getEventDetail(eventId)
            .flatMapLatest { eventDetailDto ->
                if (eventDetailDto == null) {
                    emptyFlow()
                } else {
                    flowOf(eventDetailDto.toDomain())
                }
            }

    override suspend fun toggleEventLike(eventId: Long): Result<Unit> {
        val response = eventDataSource.toggleEventLike(eventId)
        return when (response) {
            is NetworkState.Success -> Result.success(response.data)
            else -> Result.failure(IllegalStateException())
        }
    }
}