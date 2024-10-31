package com.exhibitiondot.presentation.mapper

import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.presentation.model.UserUiModel

fun User.toUiModel() =
    UserUiModel(
        email = email,
        name = name,
        phone = phone,
        nickname = nickname,
        region = region.name,
        categoryTags = categoryList.joinToString(" ", transform = Category::toTag),
        eventTypeTags = eventTypeList.joinToString(" ", transform = EventType::toTag),
    )