package com.exhibitiondot.domain.usecase.event

import androidx.paging.PagingData
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventParams
import com.exhibitiondot.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventListUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    operator fun invoke(params: EventParams): Flow<PagingData<Event>> =
        eventRepository.getEventList(params)
}