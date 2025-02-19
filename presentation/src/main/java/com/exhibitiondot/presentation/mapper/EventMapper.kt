package com.exhibitiondot.presentation.mapper

import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.presentation.model.EventDetailUiModel
import com.exhibitiondot.presentation.model.EventUiModel

fun Event.toUiModel() =
    EventUiModel(
        id = id,
        name = name,
        imgUrl = imgUrl,
        date = format(DateFormatStrategy.FullDate(date)),
    )

fun EventDetail.toUiModel() =
    EventDetailUiModel(
        id = id,
        name = name,
        imgUrl = imgUrl,
        region = region.name,
        categoryTags = categoryList.joinToString(" ", transform = Category::toTag),
        eventTypeTags = eventTypeList.joinToString(" ", transform = EventType::toTag),
        date = format(DateFormatStrategy.FullDate(date)),
        createdAt = format(DateFormatStrategy.RelativeTime(createdAt)),
        likeCount = likeCount,
        isLike = isLike,
        owner = owner
    )
