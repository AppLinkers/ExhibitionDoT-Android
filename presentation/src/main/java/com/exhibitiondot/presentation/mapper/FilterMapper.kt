package com.exhibitiondot.presentation.mapper

import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType

fun Category.toTag() = "#${key}"

fun EventType.toTag() = "#${key}"