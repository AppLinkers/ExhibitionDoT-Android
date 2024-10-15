package com.exhibitiondot.domain.repository

import androidx.paging.PagingData
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEventList(
        page: Int = 1,
        size: Int = 10,
        region: Region,
        categoryList: List<Category>,
        eventTypeList: List<EventType>,
        query: String
    ): Flow<PagingData<List<Event>>>

    fun getEventDetail(eventId: Long): Flow<EventDetail>

    suspend fun toggleEventLike(eventId: Long): Result<Unit>
}