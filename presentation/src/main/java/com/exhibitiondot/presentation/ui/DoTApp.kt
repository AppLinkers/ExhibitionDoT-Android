package com.exhibitiondot.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.exhibitiondot.presentation.ui.navigation.DoTNavHost

@Composable
fun DoTApp(
    appState: DoTAppState,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0.dp)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(padding)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            DoTNavHost(appState = appState)
        }
    }
}