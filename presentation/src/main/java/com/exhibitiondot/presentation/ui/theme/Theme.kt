package com.exhibitiondot.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DoTTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = DoTTypography,
        shapes = DoTShapes,
        content = content
    )
}