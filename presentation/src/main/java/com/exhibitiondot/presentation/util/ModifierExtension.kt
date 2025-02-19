package com.exhibitiondot.presentation.util

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.onClick(
    onClick: () -> Unit,
    enabled: Boolean = true,
): Modifier {
    return this then Modifier.clickable(
        interactionSource = null,
        indication = null,
        enabled = enabled,
        onClick = onClick
    )
}