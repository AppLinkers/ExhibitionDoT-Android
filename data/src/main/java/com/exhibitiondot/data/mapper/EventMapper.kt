package com.exhibitiondot.data.mapper

import com.exhibitiondot.data.network.model.dto.EventDetailDto
import com.exhibitiondot.data.network.model.dto.EventDto
import com.exhibitiondot.data.network.model.dto.EventInfoDto
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.model.EventInfo

fun EventDto.toDomain() =
    Event(
        id = id,
        name = name,
        imgUrl = imgUrl,
        date = date,
        likeCount = likeCount
    )

fun EventDetailDto.toDomain() =
    EventDetail(
        id = id,
        name = name,
        imgUrl = imgUrl,
        region = region.toRegion(),
        categoryList = categoryList.map(String::toCategory),
        eventTypeList = eventTypeList.map(String::toEventType),
        date = date,
        likeCount = likeCount,
        isLike = isLike,
        owner = owner,
        createdAt = createdAt
    )

fun EventInfo.toDto() =
    EventInfoDto(
        name = name,
        date = date,
        region = region.key,
        categoryList = categoryList.map { it.key },
        eventTypeList = eventTypeList.map { it.key }
    )