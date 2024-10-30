package com.exhibitiondot.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.exhibitiondot.presentation.R

@Composable
fun XCircle(
    modifier: Modifier = Modifier,
    size: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .background(
                color = MaterialTheme.colorScheme.onSurface,
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size((size * 3 / 4).dp),
            painter = painterResource(id = R.drawable.ic_x),
            tint = MaterialTheme.colorScheme.background,
            contentDescription = "x-icon",
        )
    }
}

@Composable
fun HeartIcon(
    modifier: Modifier = Modifier,
    size: Int,
    isLike: Boolean
) {
    Icon(
        modifier = modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_heart),
        tint = if (isLike) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.surfaceContainer
        },
        contentDescription = "heart-icon"
    )
}