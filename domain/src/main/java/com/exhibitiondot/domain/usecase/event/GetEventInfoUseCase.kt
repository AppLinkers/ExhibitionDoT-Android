package com.exhibitiondot.domain.usecase.event

import com.exhibitiondot.domain.model.EventInfo
import javax.inject.Inject

class GetEventInfoUseCase @Inject constructor(
    private val getEventDetailUseCase: GetEventDetailUseCase,
) {
    suspend operator fun invoke(eventId: Long): Result<EventInfo> {
        getEventDetailUseCase(eventId)
            .onSuccess { eventDetail ->
                val eventInfo = with(eventDetail) {
                    EventInfo(
                        name = name,
                        date = date,
                        region = region,
                        categoryList = categoryList,
                        eventTypeList = eventTypeList
                    )
                }
                return Result.success(eventInfo)
            }
            .onFailure { t ->
                return Result.failure(t)
            }
        return Result.failure(IllegalStateException())
    }
}