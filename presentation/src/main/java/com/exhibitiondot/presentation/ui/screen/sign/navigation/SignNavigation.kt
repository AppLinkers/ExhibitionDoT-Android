package com.exhibitiondot.presentation.ui.screen.sign.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.exhibitiondot.presentation.ui.DoTAppState
import com.exhibitiondot.presentation.ui.navigation.ScreenGraph
import com.exhibitiondot.presentation.ui.navigation.SignScreen
import com.exhibitiondot.presentation.ui.screen.sign.signIn.SignInRoute
import com.exhibitiondot.presentation.ui.screen.sign.signUp.SignUpRoute

fun NavGraphBuilder.nestedSignGraph(appState: DoTAppState) {
    val navController = appState.navController

    navigation<ScreenGraph.SignGraph>(startDestination = SignScreen.SignIn) {
        composable<SignScreen.SignIn> {
            SignInRoute(
                moveSignUp = navController::navigateToSignScreen,
            )
        }
        composable<SignScreen.SignUp> {
            SignUpRoute(
                onBack = navController::popBackStack,
            )
        }
    }
}

fun NavController.navigateToSignGraph() = navigate(ScreenGraph.SignGraph) {
    popUpTo(graph.id) { inclusive = true }
}

private fun NavController.navigateToSignScreen(email: String) = navigate(SignScreen.SignUp(email))