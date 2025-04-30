package com.exhibitiondot.data.mapper

import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region

fun String.toRegion(): Region =
    Region.fromKey(this) ?: throw IllegalArgumentException("알 수 없는 Region key: $this")

fun String.toCategory(): Category =
    Category.fromKey(this) ?: throw IllegalArgumentException("알 수 없는 Category key: $this")

fun String.toEventType(): EventType =
    EventType.fromKey(this) ?: throw IllegalArgumentException("알 수 없는 EventType key: $this")