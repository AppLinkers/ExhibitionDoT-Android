package com.exhibitiondot.data.model.request

data class ChangeUserInfoRequest(
    val nickname: String,
    val region: String,
    val categoryList: List<String>,
    val eventTypeList: List<String>
)
