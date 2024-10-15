package com.exhibitiondot.data.model.request

data class SingUpRequest(
    val email: String,
    val name: String,
    val phone: String,
    val nickname: String,
    val region: String,
    val categoryList: List<String>,
    val eventTypeList: List<String>
)
