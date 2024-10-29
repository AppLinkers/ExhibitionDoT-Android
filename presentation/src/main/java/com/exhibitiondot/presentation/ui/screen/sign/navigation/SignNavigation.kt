package com.exhibitiondot.presentation.ui.screen.sign.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.exhibitiondot.presentation.ui.DoTAppState
import com.exhibitiondot.presentation.ui.navigation.KEY_SIGN_UP_EMAIL
import com.exhibitiondot.presentation.ui.navigation.Screen
import com.exhibitiondot.presentation.ui.navigation.ScreenGraph
import com.exhibitiondot.presentation.ui.screen.main.navigation.navigateToMainGraph
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
        route = ScreenGraph.SignGraph.route,
    ) {
        composable(
            route = Screen.SignIn.route
        ) {
            SignInRoute(
                moveMain = appState.navController::navigateToMainGraph,
                moveSignUp = appState.navController::navigateToSignScreen
            )
        }
        composable(
            route = "${Screen.SignUp.route}/{$KEY_SIGN_UP_EMAIL}",
            arguments = listOf(
                navArgument(KEY_SIGN_UP_EMAIL) { type = NavType.StringType },
            ),
        ) {
            SignUpRoute(
                moveMain = appState.navController::navigateToMainGraph,
                onBack = appState.navController::popBackStack
            )
        }
    }
}