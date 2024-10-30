package com.exhibitiondot.presentation.mapper

import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.presentation.model.EventUiModel

fun Event.toUiModel() =
    EventUiModel(
        id = id,
        name = name,
        imgUrl = imgUrl,
        date = date.format(DateFormatStrategy.MonthDay),
        likeCount = likeCount
    )