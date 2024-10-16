package com.exhibitiondot.data.datasource

import com.exhibitiondot.data.api.EventApi
import com.exhibitiondot.data.model.dto.EventDetailDto
import com.exhibitiondot.data.network.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EventRemoteDataSource @Inject constructor(
    private val eventApi: EventApi
) : EventDataSource {
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