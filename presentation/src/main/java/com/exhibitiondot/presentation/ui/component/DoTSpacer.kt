package com.exhibitiondot.presentation.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DoTSpacer(
    modifier: Modifier = Modifier,
    size: Int = 0,
) {
    Spacer(modifier = modifier.size(size.dp))
}