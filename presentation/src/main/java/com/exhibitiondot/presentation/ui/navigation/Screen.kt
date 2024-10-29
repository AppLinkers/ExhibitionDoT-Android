package com.exhibitiondot.presentation.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String, ) {
    data object SignIn : Screen("sign-in")
    data object SignUp : Screen(route = "sign-up")
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object My : Screen("my")
    data object EventDetail : Screen("event-detail")
    data object Setting : Screen("setting")
}

const val KEY_SIGN_UP_EMAIL = "key-email"