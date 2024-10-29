package com.exhibitiondot.domain.usecase.event

import androidx.paging.PagingData
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventListUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    operator fun invoke(
        region: Region,
        categoryList: List<Category>,
        eventTypeList: List<EventType>,
        query: String
    ): Flow<PagingData<Event>> =
        eventRepository.getEventList(region, categoryList, eventTypeList, query)
}