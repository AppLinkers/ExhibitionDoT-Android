package com.exhibitiondot.domain.model

interface Filter

sealed class Region(val key: String, val name: String) : Filter {
    data object Seoul : Region("seoul", "서울")
    data object Gyeonggi : Region("gyeonggi", "경기도")
    data object Chungcheoung : Region("chungcheong", "충청도")
    data object Jeolla : Region("jeolla", "전라도")
    data object Gyeongsang : Region("gyeongsang", "경상도")
    data object Jeju : Region("jeju", "제주")

    companion object {
        val values: List<Region> =
            listOf(Seoul, Gyeonggi, Chungcheoung, Jeolla, Gyeonggi, Gyeongsang, Jeju)
    }
}

sealed class Category(val key: String) : Filter {
    data object IT : Category("전기/전자/IT")
    data object Interior : Category("건설/건축/인테리어")
    data object Health : Category("의료/건강/스포츠")
    data object Fashion : Category("섬유/의류/패션")
    data object Science : Category("기계/과학/기술")
    data object Design : Category("예술/디자인")
    data object Education : Category("교육/출판")
    data object Finance : Category("금융/재테크")
    data object Performance : Category("공연/이벤트")
    data object Entertainment : Category("관광/오락")
    data object Environment : Category("환경/에너지")
    data object Food : Category("농수산/식음료")
    data object ETC : Category("기타")

    companion object {
        val values: List<Category> =
            listOf(
                IT, Interior, Health, Fashion, Science, Design, Education,
                Finance, Performance, Entertainment, Entertainment, Food, ETC
            )
    }
}

sealed class EventType(val key: String) : Filter {
    data object Exhibition : EventType("전시회")
    data object Festival : EventType("행사/축제")
    data object Fair : EventType("박람회")
    data object Convention : EventType("컨벤션")
    data object ChildrenExperience : EventType("아동체험전")
    data object Museum : EventType("박물관")
    data object Musical : EventType("뮤지컬/콘서트")

    companion object {
        val values: List<EventType> =
            listOf(
                Exhibition, Festival, Fair, Convention,
                ChildrenExperience, Museum, Musical
            )
    }
}
