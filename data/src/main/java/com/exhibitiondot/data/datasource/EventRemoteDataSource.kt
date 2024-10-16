package com.exhibitiondot.data.datasource

import com.exhibitiondot.data.api.EventApi
import com.exhibitiondot.data.model.dto.EventDetailDto
import com.exhibitiondot.data.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EventRemoteDataSource(
    private val eventApi: EventApi
) : EventDataSource {
    override fun getEventList(
        page: Int?,
        size: Int?,
        region: String?,
        categoryList: List<String>?,
        eventTypeList: List<String>?,
        query: String?
    ): Flow<List<EventDto>> = flow {
        val response = eventApi.getEventList(page, size, region, categoryList, eventTypeList, query)
        when (response) {
            is NetworkState.Success -> emit(response.data.contents)
            else -> emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override fun getEventDetail(eventId: Long): Flow<EventDetailDto?> = flow<EventDetailDto?> {
        val response = eventApi.getEventDetail(eventId)
        when (response) {
            is NetworkState.Success -> emit(response.data)
            else -> emit(null)
        }
    }.flowOn(Dispatchers.IO)
}