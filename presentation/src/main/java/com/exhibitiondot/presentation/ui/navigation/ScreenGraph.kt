package com.exhibitiondot.presentation.ui.navigation

sealed class ScreenGraph(val route: String) {
    data object SignGraph : ScreenGraph("sign-graph")
    data object MainGraph : ScreenGraph("main-graph")
}