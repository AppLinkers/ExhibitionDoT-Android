package com.exhibitiondot.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.exhibitiondot.data.api.EventApi
import com.exhibitiondot.data.constant.ApiConst
import com.exhibitiondot.data.datasource.EventDataSource
import com.exhibitiondot.data.datasource.EventPagingSource
import com.exhibitiondot.data.mapper.toDomain
import com.exhibitiondot.data.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.domain.repository.EventRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventApi: EventApi,
    private val eventDataSource: EventDataSource
) : EventRepository {
    override fun getEventList(
        region: Region,
        categoryList: List<Category>,
        eventTypeList: List<EventType>,
        query: String
    ): Flow<PagingData<Event>> =
        Pager(
            config = PagingConfig(pageSize = ApiConst.DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                EventPagingSource(
                    eventApi = eventApi,
                    region = region.key,
                    categoryList = categoryList.map { it.key },
                    eventTypeList = eventTypeList.map { it.key },
                    query = query
                )
            }
        ).flow.map { pagingData -> pagingData.map(EventDto::toDomain) }

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