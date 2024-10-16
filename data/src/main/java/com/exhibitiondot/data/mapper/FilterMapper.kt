package com.exhibitiondot.data.mapper

import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region

fun String.toRegion(): Region {
    return when(this) {
        Region.Seoul.key -> Region.Seoul
        Region.Gyeonggi.key -> Region.Gyeonggi
        Region.Chungcheoung.key -> Region.Chungcheoung
        Region.Jeolla.key -> Region.Jeolla
        Region.Gyeongsang.key -> Region.Gyeongsang
        Region.Jeju.key -> Region.Jeju
        else -> throw IllegalArgumentException()
    }
}

fun String.toCategory(): Category {
    return when (this) {
        else -> throw IllegalArgumentException()
    }
}

fun String.toEventType(): EventType {
    return when (this) {
        else -> throw  IllegalArgumentException()
    }
}