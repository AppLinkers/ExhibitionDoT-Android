package com.exhibitiondot.presentation.ui.navigation

import kotlinx.serialization.Serializable

sealed interface SignScreen {
    @Serializable
    data object SignIn : SignScreen

    @Serializable
    data class SignUp(val email: String) : SignScreen
}

sealed interface MainScreen {
    @Serializable
    data object Home : MainScreen

    @Serializable
    data class EventDetail(val eventId: Long) : MainScreen

    @Serializable
    data class PostEvent(val eventId: Long?) : MainScreen

    @Serializable
    data object MyPage : MainScreen

    @Serializable
    data object UpdateUserInfo : MainScreen
}