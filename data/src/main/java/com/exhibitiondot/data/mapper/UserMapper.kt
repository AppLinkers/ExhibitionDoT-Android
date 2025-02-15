package com.exhibitiondot.data.mapper

import com.exhibitiondot.data.network.model.dto.UserDto
import com.exhibitiondot.data.network.model.request.ChangeUserInfoRequest
import com.exhibitiondot.data.network.model.request.SignUpRequest
import com.exhibitiondot.domain.model.User

fun UserDto.toDomain() =
    User(
        email = email,
        name = name,
        phone = phone,
        nickname = nickname,
        region = region.toRegion(),
        categoryList = categoryList.map(String::toCategory),
        eventTypeList = eventTypeList.map(String::toEventType)
    )

fun User.toSignUpRequest() =
    SignUpRequest(
        email = email,
        name = name,
        phone = phone,
        nickname = nickname,
        region = region.key,
        categoryList = categoryList.map { it.key },
        eventTypeList = eventTypeList.map { it.key }
    )

fun User.toChangeUserInfoRequest() =
    ChangeUserInfoRequest(
        nickname = nickname,
        region = region.key,
        categoryList = categoryList.map { it.key },
        eventTypeList = eventTypeList.map { it.key }
    )