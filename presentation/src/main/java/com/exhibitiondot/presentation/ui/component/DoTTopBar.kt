package com.exhibitiondot.presentation.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.state.IEditTextState
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun DoTTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBack: (() -> Unit)? = null,
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
                start = screenPadding,
                end = screenPadding
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier.size(60.dp),
            painter = painterResource(R.drawable.app_logo_white),
            contentDescription = "app-logo-white"
        )
        Row {
            UserIcon(
                modifier = Modifier.clickable(onClick = moveMy),
                size = 24
            )
            DoTSpacer(size = 16)
            SearchIcon(
                modifier = Modifier.clickable(onClick = showSearchDialog),
                size = 24
            )
        }
    }
}

@Composable
fun HomeSearchBar(
    modifier: Modifier = Modifier,
    queryState: IEditTextState,
    focusRequester: FocusRequester,
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
            value = queryState.typedText,
            focusRequester = focusRequester,
            onValueChange = queryState::typeText,
            onResetValue = queryState::resetText,
            onSearch = applyQuery
        )
    }
}

@Composable
fun EventDetailTopBar(
    modifier: Modifier = Modifier,
    eventName: String,
    skipImage: Boolean,
    onBack: () -> Unit,
) {
    val containerColor by animateColorAsState(
        targetValue = if (skipImage) {
            MaterialTheme.colorScheme.background
        } else {
            Color.Transparent
        },
        label = "event-detail-top-bar-color-anim"
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
            color = if (skipImage) {
                MaterialTheme.colorScheme.surfaceContainerHigh
            } else {
                MaterialTheme.colorScheme.background
            },
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
    }
}