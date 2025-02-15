package com.exhibitiondot.domain.usecase.event

import com.exhibitiondot.domain.model.EventInfo
import com.exhibitiondot.domain.model.ImageSource
import com.exhibitiondot.domain.repository.EventRepository
import javax.inject.Inject

class UpdateEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(
        image: ImageSource,
        originEventInfo: EventInfo,
        updatedEventInfo: EventInfo,
        eventId: Long,
    ): Result<Unit> =
        eventRepository.updateEvent(
            file = if (image is ImageSource.Local) image.file else null,
            eventInfo = if (updatedEventInfo != originEventInfo) updatedEventInfo else null,
            eventId = eventId
        )
}