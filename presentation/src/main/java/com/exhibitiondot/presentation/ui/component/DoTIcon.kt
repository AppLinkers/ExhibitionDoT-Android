package com.exhibitiondot.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        XIcon(
            size = size * 3 / 4,
            color = MaterialTheme.colorScheme.background
        )
    }
}

@Composable
fun XIcon(
    modifier: Modifier = Modifier,
    size: Int,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Icon(
        modifier = modifier.size(size.dp),
        painter = painterResource(id = R.drawable.ic_x),
        tint = color,
        contentDescription = "x-icon",
    )
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

@Composable
fun DownIcon(
    size: Int
) {
    Icon(
        modifier = Modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_down),
        tint = MaterialTheme.colorScheme.onBackground,
        contentDescription = "down-icon"
    )
}

@Composable
fun RedoIcon(
    modifier: Modifier = Modifier,
    size: Int
) {
    Icon(
        modifier = modifier
            .size(size.dp),
        painter = painterResource(R.drawable.ic_redo),
        tint = MaterialTheme.colorScheme.onBackground,
        contentDescription = "redo-icon"
    )
}

@Composable
fun UserIcon(
    modifier: Modifier = Modifier,
    size: Int
) {
    Icon(
        modifier = modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_user),
        tint = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentDescription = "user-icon"
    )
}

@Composable
fun SearchIcon(
    modifier: Modifier = Modifier,
    size: Int,
    color: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    Icon(
        modifier = modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_search),
        tint = color,
        contentDescription = "search-icon"
    )
}

@Composable
fun BackIcon(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    onBack: () -> Unit
) {
    Icon(
        modifier = modifier
            .size(30.dp)
            .clickable(onClick = onBack),
        painter = painterResource(R.drawable.ic_back),
        tint = color,
        contentDescription = "back-icon"
    )
}

@Composable
fun AddIcon(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
) {
    Icon(
        modifier = modifier,
        imageVector = Icons.Rounded.Add,
        tint = color,
        contentDescription = "add-icon"
    )
}

@Composable
fun CalendarIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier,
        painter = painterResource(R.drawable.ic_calender),
        tint = MaterialTheme.colorScheme.surfaceContainerLow,
        contentDescription = "calender-icon"
    )
}

@Composable
fun MenuIcon(
    modifier: Modifier = Modifier,
    color: Color,
    onClick: () -> Unit,
) {
    Icon(
        modifier = modifier.clickable(onClick = onClick),
        painter = painterResource(R.drawable.ic_menu),
        tint = color,
        contentDescription = "menu-icon"
    )
}

@Composable
fun SendIcon(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Icon(
        modifier = modifier.clickable(
            onClick = onClick,
            enabled = enabled
        ),
        imageVector = Icons.AutoMirrored.Rounded.Send,
        tint = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        contentDescription = "send-icon"
    )
}