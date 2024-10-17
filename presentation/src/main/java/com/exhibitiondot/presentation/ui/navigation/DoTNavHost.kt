package com.exhibitiondot.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.exhibitiondot.presentation.ui.DoTAppState

@Composable
fun DoTNavHost(
    modifier: Modifier = Modifier,
    appState: DoTAppState,
    startDestination: ScreenGraph
) {
    val navController = appState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {

    }
}