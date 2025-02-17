package com.exhibitiondot.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    var firstAccess by rememberSaveable { mutableStateOf(true) }
    val isAuthenticated by authViewModel.authFlow.collectAsStateWithLifecycle(initialValue = false)
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            appState.navController.navigateToMainGraph()
        } else if (firstAccess.not()) {
            appState.navController.navigateToSignGraph()
        } else {
            firstAccess = false
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