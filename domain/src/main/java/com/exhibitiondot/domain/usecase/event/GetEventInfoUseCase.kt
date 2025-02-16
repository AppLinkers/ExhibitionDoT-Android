package com.exhibitiondot.domain.usecase.event

import com.exhibitiondot.domain.model.EventInfo
import com.exhibitiondot.domain.model.ImageSource
import javax.inject.Inject

class GetEventInfoUseCase @Inject constructor(
    private val getEventDetailUseCase: GetEventDetailUseCase,
) {
    suspend operator fun invoke(eventId: Long): Result<Pair<EventInfo, ImageSource>> {
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
                val imageSource = ImageSource.Remote(url = eventDetail.imgUrl)
                return Result.success(eventInfo to imageSource)
            }
            .onFailure { t ->
                return Result.failure(t)
            }
        return Result.failure(IllegalStateException())
    }
}