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
import com.exhibitiondot.presentation.ui.screen.main.eventDetail.EventDetailRoute
import com.exhibitiondot.presentation.ui.screen.main.home.HomeRoute

fun NavController.navigateToMainGraph() = navigate(Screen.Home.route) {
    popUpTo(graph.id) { inclusive = true }
}

fun NavController.navigateToEventDetail(eventId: Long) =
    navigate("${Screen.EventDetail.route}/$eventId")

fun NavGraphBuilder.nestedMainGraph(appState: DoTAppState) {
    val navController = appState.navController

    navigation(
        startDestination = Screen.Home.route,
        route = ScreenGraph.MainGraph.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeRoute(
                scope = appState.coroutineScope,
                moveEventDetail = navController::navigateToEventDetail,
                moveMy = { navController.navigate(Screen.MyPage.route) },
            )
        }
        composable(
            route = "${Screen.EventDetail.route}/{$KEY_EVENT_ID}",
            arguments = listOf(
                navArgument(KEY_EVENT_ID) { type = NavType.LongType }
            )
        ) {
            EventDetailRoute(onBack = navController::popBackStack)
        }
        composable(
            route = Screen.MyPage.route
        ) {

        }
    }
}