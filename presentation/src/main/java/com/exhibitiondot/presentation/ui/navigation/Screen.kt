package com.exhibitiondot.presentation.ui.navigation

import com.exhibitiondot.presentation.ui.navigation.ScreenGraph.MainScreen
import com.exhibitiondot.presentation.ui.navigation.ScreenGraph.SignInScreen

sealed class Screen(val route: String) {
    data object SignIn : Screen("sign-in")
    data object SignUp : Screen("sign-up")
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object My : Screen("my")
    data object EventDetail : Screen("event-detail")
    data object Setting : Screen("setting")
}