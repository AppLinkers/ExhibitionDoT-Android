package com.exhibitiondot.domain.model

data class User(
    val email: String,
    val name: String,
    val phone: String,
    val nickname: String,
    val region: Region,
    val categoryList: List<Category>,
    val eventTypeList: List<EventType>
) {
    companion object {
        val NONE = User(
            email = "",
            name = "",
            phone = "",
            nickname = "",
            region = Region.SEOUL,
            categoryList = listOf(),
            eventTypeList = listOf()
        )
    }
}
