package com.exhibitiondot.presentation.mapper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

sealed interface DateFormatStrategy {
    fun format(): String

    data class FullDate(val date: String) : DateFormatStrategy {
        override fun format(): String {
            val (yyyy, mm, dd) = date.split(SPLIT_DELIMITER)
            return "${yyyy}.${mm}.$dd"
        }
    }

    data class RelativeTime(val date: String) : DateFormatStrategy {
        override fun format(): String {
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

    data object Today : DateFormatStrategy {
        override fun format(): String {
            val now = Date()
            return formatter.format(now)
        }
    }

    data class MillisToDate(val millis: Long) : DateFormatStrategy {
        override fun format(): String {
            return formatter.format(Date(millis))
        }
    }

    data class DateToMillis(val date: String) : DateFormatStrategy {
        override fun format(): String {
            val utcFormatter =
                SimpleDateFormat(DATE_PATTERN, Locale.KOREA).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }
            val date = utcFormatter.parse(date) as Date
            return date.time.toString()
        }
    }

    companion object {
        private const val SPLIT_DELIMITER = "-"
        private const val DATE_PATTERN = "yyyy-MM-dd"

        private val formatter = SimpleDateFormat(DATE_PATTERN, Locale.KOREA)
    }
}

fun format(formatStrategy: DateFormatStrategy): String = formatStrategy.format()