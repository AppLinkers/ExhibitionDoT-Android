package com.exhibitiondot.presentation.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exhibitiondot.presentation.util.onClick

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
        label = "filter-color-anim"
    )
    Box(
        modifier = modifier
            .wrapContentSize()
            .border(
                width = 1.dp,
                color = color,
                shape = CircleShape
            )
            .onClick(onClick = onSelect),
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
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun HomeFilterChip(
    modifier: Modifier = Modifier,
    text: String,
    isApplied: Boolean,
    onClick: () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val containerColor by animateColorAsState(
        targetValue = if (isApplied) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "home-filter-color-anim"
    )
    Box(
        modifier = modifier
            .height(42.dp)
            .background(
                color = containerColor,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = CircleShape
            )
            .onClick(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            leadingIcon?.let { icon -> icon() }
            Text(
                text = text,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = if (isApplied) {
                    FontWeight.W600
                } else {
                    FontWeight.W400
                }
            )
            trailingIcon?.let { icon -> icon() }
        }
    }
}