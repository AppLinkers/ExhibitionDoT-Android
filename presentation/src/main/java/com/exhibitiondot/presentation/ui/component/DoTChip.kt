package com.exhibitiondot.presentation.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DoTFilterChip(
    modifier: Modifier = Modifier,
    name: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceContainer
        },
        label = "color-anim"
    )
    Box(
        modifier = modifier
            .wrapContentSize()
            .border(
                width = 1.dp,
                color = color,
                shape = CircleShape
            )
            .clickable(onClick = onSelect),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                    vertical = 10.dp
                ),
            text = name,
            color = color,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}