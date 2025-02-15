package com.exhibitiondot.domain.usecase.event

import com.exhibitiondot.domain.repository.EventRepository
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(eventId: Long): Result<Unit> =
        eventRepository.deleteEvent(eventId)
}