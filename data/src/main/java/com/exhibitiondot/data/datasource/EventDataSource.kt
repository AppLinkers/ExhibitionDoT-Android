package com.exhibitiondot.data.datasource

import com.exhibitiondot.data.model.dto.EventDetailDto
import com.exhibitiondot.data.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import kotlinx.coroutines.flow.Flow

interface EventDataSource {
    fun getEventDetail(eventId: Long): Flow<EventDetailDto?>

    suspend fun toggleEventLike(eventId: Long): NetworkState<Unit>
}