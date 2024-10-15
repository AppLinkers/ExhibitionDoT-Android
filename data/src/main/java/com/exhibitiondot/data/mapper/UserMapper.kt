package com.exhibitiondot.data.mapper

import com.exhibitiondot.data.model.request.ChangeUserInfoRequest
import com.exhibitiondot.data.model.request.SignUpRequest
import com.exhibitiondot.domain.model.User

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