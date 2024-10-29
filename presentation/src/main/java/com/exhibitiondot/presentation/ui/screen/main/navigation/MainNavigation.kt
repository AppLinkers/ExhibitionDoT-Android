package com.exhibitiondot.presentation.ui.screen.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.exhibitiondot.presentation.ui.DoTAppState
import com.exhibitiondot.presentation.ui.navigation.KEY_EVENT_ID
import com.exhibitiondot.presentation.ui.navigation.Screen
import com.exhibitiondot.presentation.ui.navigation.ScreenGraph
import com.exhibitiondot.presentation.ui.screen.main.home.HomeRoute

fun NavController.navigateToMainGraph() = navigate(Screen.Home.route) {
    popUpTo(ScreenGraph.MainGraph.route) { inclusive = true }
}

fun NavController.navigateToEventDetail(eventId: Long) =
    navigate("${Screen.EventDetail.route}/$eventId")

fun NavGraphBuilder.nestedMainGraph(appState: DoTAppState) {
    navigation(
        startDestination = Screen.Home.route,
        route = ScreenGraph.SignGraph.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeRoute(
                moveEventDetail = appState.navController::navigateToEventDetail,
                moveMy = { appState.navController.navigate(Screen.My.route) }
            )
        }
        composable(
            route = "${Screen.EventDetail.route}/{$KEY_EVENT_ID}",
            arguments = listOf(
                navArgument(KEY_EVENT_ID) { type = NavType.LongType }
            )
        ) {

        }
        composable(
            route = Screen.My.route
        ) {

        }
    }
}