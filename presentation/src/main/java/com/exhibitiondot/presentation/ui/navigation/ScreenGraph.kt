package com.exhibitiondot.presentation.ui.navigation

import kotlinx.serialization.Serializable

sealed interface ScreenGraph {
    @Serializable
    data object SignGraph : ScreenGraph

    @Serializable
    data object MainGraph : ScreenGraph
}