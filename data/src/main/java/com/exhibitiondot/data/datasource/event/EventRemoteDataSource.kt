package com.exhibitiondot.data.datasource.event

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.exhibitiondot.data.network.api.EventApi
import com.exhibitiondot.data.network.api.ApiConst
import com.exhibitiondot.data.model.dto.EventDetailDto
import com.exhibitiondot.data.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventRemoteDataSource @Inject constructor(
    private val eventApi: EventApi
) : EventDataSource {
    override fun getEventList(
        region: String?,
        categoryList: List<String>,
        eventTypeList: List<String>,
        query: String
    ): Flow<PagingData<EventDto>> =
        Pager(
            config = PagingConfig(pageSize = ApiConst.DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                EventPagingSource(
                    eventApi = eventApi,
                    region = region,
                    categoryList = categoryList,
                    eventTypeList = eventTypeList,
                    query = query
                )
            }
        ).flow

    override suspend fun getEventDetail(eventId: Long): NetworkState<EventDetailDto> {
        return eventApi.getEventDetail(eventId)
    }

    override suspend fun toggleEventLike(eventId: Long): NetworkState<Unit> {
        return eventApi.toggleEventLike(eventId)
    }
}