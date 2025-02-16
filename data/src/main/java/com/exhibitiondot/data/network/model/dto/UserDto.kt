package com.exhibitiondot.data.network.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val email: String,
    val name: String,
    val phone: String,
    val nickname: String,
    val region: String,
    val categoryList: List<String>,
    val eventTypeList: List<String>
)
