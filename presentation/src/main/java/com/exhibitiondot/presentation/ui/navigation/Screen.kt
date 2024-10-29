package com.exhibitiondot.presentation.ui.navigation

sealed class Screen(val route: String, ) {
    data object SignIn : Screen("sign-in")
    data object SignUp : Screen(route = "sign-up")
    data object Home : Screen("home")
    data object EventDetail : Screen("event-detail")
    data object My : Screen("my")
}

const val KEY_SIGN_UP_EMAIL = "key-email"
const val KEY_EVENT_ID = "key-event-id"