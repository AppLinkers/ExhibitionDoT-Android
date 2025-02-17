package com.exhibitiondot.data.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserInfoRequest(
    val nickname: String,
    val region: String,
    val categoryList: List<String>,
    val eventTypeList: List<String>
)
