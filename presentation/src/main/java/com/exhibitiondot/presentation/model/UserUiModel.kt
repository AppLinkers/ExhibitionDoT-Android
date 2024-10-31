package com.exhibitiondot.presentation.model

data class UserUiModel(
    val email: String,
    val name: String,
    val phone: String,
    val nickname: String,
    val region: String,
    val categoryTags: String,
    val eventTypeTags: String
)
