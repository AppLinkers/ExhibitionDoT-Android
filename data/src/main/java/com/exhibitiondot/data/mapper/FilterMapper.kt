package com.exhibitiondot.data.mapper

import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region

fun String.toRegion(): Region {
    return when(this) {
        Region.Seoul.name -> Region.Seoul
        Region.Gyeonggi.name -> Region.Gyeonggi
        Region.Chungcheoung.name -> Region.Chungcheoung
        Region.Jeolla.name -> Region.Jeolla
        Region.Gyeongsang.name -> Region.Gyeongsang
        Region.Jeju.name -> Region.Jeju
        else -> throw IllegalArgumentException()
    }
}

fun String.toCategory(): Category {
    return when (this) {
        Category.IT.key -> Category.IT
        Category.Interior.key -> Category.Interior
        Category.Health.key -> Category.Health
        Category.Fashion.key -> Category.Fashion
        Category.Science.key -> Category.Science
        Category.Design.key -> Category.Design
        Category.Education.key -> Category.Education
        Category.Finance.key -> Category.Finance
        Category.Performance.key -> Category.Performance
        Category.Entertainment.key -> Category.Entertainment
        Category.Environment.key -> Category.Environment
        Category.Food.key -> Category.Food
        Category.ETC.key -> Category.ETC
        else -> throw IllegalArgumentException()
    }
}

fun String.toEventType(): EventType {
    return when (this) {
        EventType.Exhibition.key -> EventType.Exhibition
        EventType.Festival.key -> EventType.Festival
        EventType.Fair.key -> EventType.Fair
        EventType.Convention.key -> EventType.Convention
        EventType.ChildrenExperience.key -> EventType.ChildrenExperience
        EventType.Museum.key -> EventType.Museum
        EventType.Musical.key -> EventType.Musical
        else -> throw  IllegalArgumentException()
    }
}