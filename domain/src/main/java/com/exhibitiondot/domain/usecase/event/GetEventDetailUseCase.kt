package com.exhibitiondot.domain.usecase.event

import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.repository.EventRepository
import javax.inject.Inject

class GetEventDetailUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: Long): Result<EventDetail> =
        eventRepository.getEventDetail(eventId)
}