package com.exhibitiondot.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.exhibitiondot.presentation.ui.navigation.DoTNavHost
import com.exhibitiondot.presentation.ui.navigation.ScreenGraph

@Composable
fun DoTApp(
    appState: DoTAppState,
    startDestination: ScreenGraph
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
        ) {
            DoTNavHost(
                appState = appState,
                startDestination = startDestination
            )
        }
    }
}