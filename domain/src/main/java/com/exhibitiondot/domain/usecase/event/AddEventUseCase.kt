package com.exhibitiondot.domain.usecase.event

import com.exhibitiondot.domain.model.EventInfo
import com.exhibitiondot.domain.model.ImageSource
import com.exhibitiondot.domain.repository.EventRepository
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(image: ImageSource.Local, eventInfo: EventInfo): Result<Unit> =
        eventRepository.addEvent(image.file, eventInfo)
}