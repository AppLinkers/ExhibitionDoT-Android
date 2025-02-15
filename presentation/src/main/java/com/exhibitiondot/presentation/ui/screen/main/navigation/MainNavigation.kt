package com.exhibitiondot.presentation.ui.screen.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.exhibitiondot.presentation.ui.DoTAppState
import com.exhibitiondot.presentation.ui.navigation.MainScreen
import com.exhibitiondot.presentation.ui.navigation.ScreenGraph
import com.exhibitiondot.presentation.ui.screen.main.eventDetail.EventDetailRoute
import com.exhibitiondot.presentation.ui.screen.main.home.HomeRoute
import com.exhibitiondot.presentation.ui.screen.main.myPage.MyPageRoute

fun NavGraphBuilder.nestedMainGraph(appState: DoTAppState) {
    val navController = appState.navController

    navigation<ScreenGraph.MainGraph>(startDestination = MainScreen.Home) {
        composable<MainScreen.Home> {
            HomeRoute(
                scope = appState.coroutineScope,
                moveEventDetail = navController::navigateToEventDetail,
                movePostDetail = navController::navigateToPostEvent,
                moveMy = navController::navigateToMyPage,
            )
        }
        composable<MainScreen.EventDetail> {
            EventDetailRoute(onBack = navController::popBackStack)
        }
        composable<MainScreen.PostEvent> {

        }
        composable<MainScreen.MyPage> {
            MyPageRoute(onBack = navController::popBackStack)
        }
    }
}

fun NavController.navigateToMainGraph() = navigate(ScreenGraph.MainGraph) {
    popUpTo(graph.id) { inclusive = true }
}

private fun NavController.navigateToEventDetail(eventId: Long) = navigate(MainScreen.EventDetail(eventId))

private fun NavController.navigateToPostEvent(eventId: Long?) = navigate(MainScreen.PostEvent(eventId))

private fun NavController.navigateToMyPage() = navigate(MainScreen.MyPage)