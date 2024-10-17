package com.exhibitiondot.presentation.ui.navigation

sealed class ScreenGraph(val route: String) {
    data object SignInScreen : ScreenGraph("sign-in-graph")
    data object MainScreen : ScreenGraph("main-graph")
}