package com.exhibitiondot.presentation.ui.screen.sign.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.exhibitiondot.presentation.ui.DoTAppState
import com.exhibitiondot.presentation.ui.navigation.Screen
import com.exhibitiondot.presentation.ui.navigation.ScreenGraph
import com.exhibitiondot.presentation.ui.screen.sign.signIn.SignInRoute

fun NavController.navigateToSignGraph() = navigate(Screen.SignIn.route) {
    popUpTo(ScreenGraph.SignGraph.route) { inclusive = true }
}

fun NavGraphBuilder.nestedSignGraph(appState: DoTAppState) {
    navigation(
        startDestination = Screen.SignIn.route,
        route = ScreenGraph.SignGraph.route
    ) {
        composable(
            route = Screen.SignIn.route
        ) {
            SignInRoute(
                moveMain = {},
                moveSignUp = {}
            )
        }
    }
}