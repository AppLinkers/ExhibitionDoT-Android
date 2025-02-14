package com.exhibitiondot.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.exhibitiondot.presentation.ui.DoTAppState
import com.exhibitiondot.presentation.ui.screen.main.navigation.nestedMainGraph
import com.exhibitiondot.presentation.ui.screen.sign.navigation.nestedSignGraph

@Composable
fun DoTNavHost(
    modifier: Modifier = Modifier,
    appState: DoTAppState,
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = ScreenGraph.SignGraph
    ) {
        nestedSignGraph(appState)
        nestedMainGraph(appState)
    }
}