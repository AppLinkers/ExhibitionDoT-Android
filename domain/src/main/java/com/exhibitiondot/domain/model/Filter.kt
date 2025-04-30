package com.exhibitiondot.domain.model

interface Filter {
    val key: String
}

interface SingleFilter : Filter

interface MultiFilter : Filter

enum class Region(override val key: String) : SingleFilter {
    SEOUL("seoul"),
    GYEONGGI("gyeonggi"),
    CHUNGCHEONG("chungcheong"),
    JEOLLA("jeolla"),
    GYEONGSANG("gyeongsang"),
    GANGWON("gangwon"),
    JEJU("jeju");

    companion object {
        private val map = entries.associateBy(Region::key)

        fun fromKey(key: String): Region? = map[key]
    }
}

enum class Category(override val key: String) : MultiFilter {
    IT("전기/전자/IT"),
    INTERIOR("건설/건축/인테리어"),
    HEALTH("의료/건강/스포츠"),
    FASHION("섬유/의류/패션"),
    SCIENCE("기계/과학/기술"),
    DESIGN("예술/디자인"),
    EDUCATION("교육/출판"),
    FINANCE("금융/재테크"),
    PERFORMANCE("공연/이벤트"),
    ENTERTAINMENT("관광/오락"),
    ENVIRONMENT("환경/에너지"),
    FOOD("농수산/식음료"),
    ETC("기타");

    companion object {
        private val map = entries.associateBy(Category::key)

        fun fromKey(key: String): Category? = map[key]
    }
}

enum class EventType(override val key: String) : MultiFilter {
    EXHIBITION("전시회"),
    FESTIVAL("행사/축제"),
    FAIR("박람회"),
    CONVENTION("컨벤션"),
    CHILDREN_EXPERIENCE("아동체험전"),
    MUSEUM("박물관"),
    MUSICAL("뮤지컬/콘서트");

    companion object {
        private val map = entries.associateBy(EventType::key)

        fun fromKey(key: String): EventType? = map[key]
    }
}
