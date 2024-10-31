package com.exhibitiondot.presentation.mapper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed interface DateFormatStrategy {
    fun format(date: String): String

    data object FullDate : DateFormatStrategy {
        override fun format(date: String): String {
            val (yyyy, mm, dd) = date.split(SPLIT_DELIMITER)
            return "${yyyy}.${mm}.${dd}일"
        }
    }

    data object MonthDay : DateFormatStrategy {
        override fun format(date: String): String {
            val (_, mm, dd) = date.split(SPLIT_DELIMITER)
            return "${mm}월 ${dd}일"
        }
    }

    data object RelativeTime : DateFormatStrategy {
        override fun format(date: String): String {
            val parsedDate = formatter.parse(date)
            val now = Date()
            val diff = now.time - (parsedDate as Date).time

            val days = diff / (24 * 60 * 60 * 1_000)
            val months = days / 30
            val years = months / 12
            return when {
                days == 0L -> "오늘"
                days <= 30 -> "${days}일 전"
                months < 12 -> "${months}달 전"
                else -> "${years}년 전"
            }
        }
    }

    companion object {
        private const val SPLIT_DELIMITER = "-"
        private const val DATE_PATTERN = "yyyy-MM-dd"

        private val formatter = SimpleDateFormat(DATE_PATTERN, Locale.KOREA)

    }
}

fun String.format(formatStrategy: DateFormatStrategy): String =
    formatStrategy.format(this)