package com.exhibitiondot.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.exhibitiondot.data.api.EventApi
import com.exhibitiondot.data.constant.ApiConst
import com.exhibitiondot.data.model.dto.EventDetailDto
import com.exhibitiondot.data.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EventRemoteDataSource @Inject constructor(
    private val eventApi: EventApi
) : EventDataSource {
    override fun getEventList(
        region: String,
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

    override fun getEventDetail(eventId: Long): Flow<EventDetailDto?> = flow<EventDetailDto?> {
        val response = eventApi.getEventDetail(eventId)
        when (response) {
            is NetworkState.Success -> emit(response.data)
            else -> emit(null)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun toggleEventLike(eventId: Long): NetworkState<Unit> {
        return eventApi.toggleEventLike(eventId)
    }
}