package com.exhibitiondot.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.exhibitiondot.presentation.ui.DoTAppState
import com.exhibitiondot.presentation.ui.screen.AuthViewModel
import com.exhibitiondot.presentation.ui.screen.main.navigation.navigateToMainGraph
import com.exhibitiondot.presentation.ui.screen.main.navigation.nestedMainGraph
import com.exhibitiondot.presentation.ui.screen.sign.navigation.navigateToSignGraph
import com.exhibitiondot.presentation.ui.screen.sign.navigation.nestedSignGraph

@Composable
fun DoTNavHost(
    modifier: Modifier = Modifier,
    appState: DoTAppState,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        authViewModel.authFlow.collect { isAuthenticated ->
            if (isAuthenticated) {
                appState.navController.navigateToMainGraph()
            } else {
                appState.navController.navigateToSignGraph()
            }
        }
    }
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = ScreenGraph.SignGraph
    ) {
        nestedSignGraph(appState)
        nestedMainGraph(appState)
    }
}