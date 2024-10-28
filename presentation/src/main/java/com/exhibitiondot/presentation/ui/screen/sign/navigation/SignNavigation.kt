package com.exhibitiondot.presentation.ui.screen.sign.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.exhibitiondot.presentation.ui.DoTAppState
import com.exhibitiondot.presentation.ui.navigation.Screen
import com.exhibitiondot.presentation.ui.navigation.ScreenGraph
import com.exhibitiondot.presentation.ui.screen.sign.signIn.SignInRoute
import com.exhibitiondot.presentation.ui.screen.sign.signUp.SignUpRoute

fun NavController.navigateToSignGraph() = navigate(Screen.SignIn.route) {
    popUpTo(ScreenGraph.SignGraph.route) { inclusive = true }
}

fun NavController.navigateToSignScreen(email: String) =
    navigate("${Screen.SignUp.route}/$email")

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
                moveSignUp = { email ->
                    appState.navController.navigateToSignScreen(email)
                }
            )
        }
        composable(
            route = Screen.SignUp.route,
            arguments = Screen.SignUp.arguments
        ) {
            SignUpRoute(
                moveMain = {},
                onBack = { appState.navController.popBackStack() }
            )
        }
    }
}