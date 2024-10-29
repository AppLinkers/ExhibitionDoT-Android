package com.exhibitiondot.domain.usecase.event

import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventDetailUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    operator fun invoke(eventId: Long): Flow<EventDetail> =
        eventRepository.getEventDetail(eventId)
}