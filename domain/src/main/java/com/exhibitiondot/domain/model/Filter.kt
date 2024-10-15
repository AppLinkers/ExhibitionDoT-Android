package com.exhibitiondot.domain.model

interface Filter

sealed interface SingleFilter : Filter

sealed interface MultiFilter : Filter

sealed class Region(val key: String, val name: String) : SingleFilter {
    data object Seoul : Region("seoul", "서울")
    data object Gyeonggi : Region("gyeonggi", "경기도")
    data object Chungcheoung : Region("chungcheong", "충청도")
    data object Jeolla : Region("jeolla", "전라도")
    data object Gyeongsang : Region("gyeongsang", "경상도")
    data object Jeju : Region("jeju", "제주")
}

sealed class Category(val key: String, val name: String) : MultiFilter

sealed class EventType(val key: String, val name: String) : MultiFilter
