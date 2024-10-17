package com.exhibitiondot.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Stable
class DoTAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
)

@Composable
fun rememberDoTAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) : DoTAppState {
    return remember(
        navController,
        coroutineScope
    ) {
        DoTAppState(
            navController = navController,
            coroutineScope = coroutineScope
        )
    }
}