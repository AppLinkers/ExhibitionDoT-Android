package com.exhibitiondot.domain.model

data class EventParams(
    val region: Region?,
    val categoryList: List<Category>,
    val eventTypeList: List<EventType>,
    val query: String
) {
    fun canReset(): Boolean {
        return region != null ||
                categoryList.isNotEmpty() ||
                eventTypeList.isNotEmpty() ||
                query.isNotEmpty()
    }

    fun reginText(default: String): String {
        return region?.name ?: default
    }

    fun categoryText(default: String): String {
        return if (categoryList.isEmpty()) {
            default
        } else if (categoryList.size == 1) {
            categoryList.first().key
        } else {
            "${categoryList.first().key} 외 ${categoryList.size - 1}"
        }
    }

    fun eventTypeText(default: String): String {
        return if (eventTypeList.isEmpty()) {
            default
        } else if (eventTypeList.size == 1) {
            eventTypeList.first().key
        } else {
            "${eventTypeList.first().key} 외 ${eventTypeList.size - 1}"
        }
    }

    companion object {
        val NONE = EventParams(
            region = null,
            categoryList = emptyList(),
            eventTypeList = emptyList(),
            query = ""
        )
    }
}
