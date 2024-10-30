package com.exhibitiondot.presentation.mapper

sealed interface DateFormatStrategy {
    fun format(date: String): String

    data object FullDate : DateFormatStrategy {
        override fun format(date: String): String {
            val (yyyy, mm, dd) = date.split(SPLIT_DELIMITER)
            return "${yyyy}년 ${mm}월 ${dd}일"
        }
    }

    data object MonthDay : DateFormatStrategy {
        override fun format(date: String): String {
            val (_, mm, dd) = date.split(SPLIT_DELIMITER)
            return "${mm}월 ${dd}일"
        }
    }

    companion object {
        private const val SPLIT_DELIMITER = "-"
    }
}

fun String.format(formatStrategy: DateFormatStrategy): String =
    formatStrategy.format(this)