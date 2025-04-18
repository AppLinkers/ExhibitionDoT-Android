package com.exhibitiondot.presentation.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.theme.screenPadding
import com.exhibitiondot.presentation.util.onClick

@Composable
fun DoTTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBack: (() -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 50.dp,
                bottom = screenPadding,
                start = screenPadding,
                end = screenPadding
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        onBack?.let {
            BackIcon(onBack = it)
            DoTSpacer(size = 14)
        }
        title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        trailingIcon?.let { icon ->
            DoTSpacer(modifier = Modifier.weight(1f))
            icon()
        }
    }
}

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    showSearchDialog: () -> Unit,
    moveMy: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 30.dp,
                bottom = 6.dp,
                start = 12.dp,
                end = screenPadding
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(R.drawable.app_logo_white),
            contentDescription = "app-logo-white"
        )
        Row {
            UserIcon(onClick = moveMy)
            DoTSpacer(size = 16)
            SearchIcon(
                modifier = Modifier.onClick(onClick = showSearchDialog),
                size = 24
            )
        }
    }
}

@Composable
fun HomeSearchBar(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    focusRequester: FocusRequester,
    onValueChange: (TextFieldValue) -> Unit,
    onResetValue: () -> Unit,
    applyQuery: () -> Unit,
    onBack: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 40.dp,
                start = screenPadding,
                end = screenPadding
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BackIcon(onBack = onBack)
        DoTSpacer(size = 10)
        SearchTextField(
            value = value,
            focusRequester = focusRequester,
            onValueChange = onValueChange,
            onResetValue = onResetValue,
            onSearch = applyQuery
        )
    }
}

@Composable
fun EventDetailTopBar(
    modifier: Modifier = Modifier,
    eventName: String,
    skipImage: Boolean,
    showDialog: () -> Unit,
    onBack: () -> Unit,
) {
    val containerColor by animateColorAsState(
        targetValue = if (skipImage) {
            MaterialTheme.colorScheme.background
        } else {
            Color.Transparent
        },
        label = "event-detail-top-bar-container-color-anim"
    )
    val iconColor by animateColorAsState(
        targetValue = if (skipImage) {
            MaterialTheme.colorScheme.surfaceContainerHigh
        } else {
            MaterialTheme.colorScheme.background
        },
        label = "event-detail-top-bar-icon-color-anim"
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = containerColor)
            .shadow(elevation = if (skipImage) 0.5.dp else 0.dp)
            .padding(
                top = 40.dp,
                bottom = screenPadding,
                start = screenPadding,
                end = screenPadding
            ),
    ) {
        BackIcon(
            modifier = Modifier.align(Alignment.CenterStart),
            color = iconColor,
            onBack = onBack
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .width(300.dp),
            text = eventName,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = if (skipImage) {
                MaterialTheme.colorScheme.onBackground
            } else {
                Color.Transparent
            }
        )
        MenuIcon(
            modifier = Modifier.align(Alignment.CenterEnd),
            color = iconColor,
            onClick = showDialog
        )
    }
}