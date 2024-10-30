package com.exhibitiondot.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.exhibitiondot.presentation.R
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
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable(onClick = it),
                painter = painterResource(R.drawable.ic_back),
                tint = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentDescription = "back-icon"
            )
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
                top = 50.dp,
                bottom = screenPadding,
                start = screenPadding,
                end = screenPadding
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Row {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = moveMy),
                painter = painterResource(R.drawable.ic_user),
                tint = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentDescription = "user-icon"
            )
            DoTSpacer(size = 16)
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = showSearchDialog),
                painter = painterResource(R.drawable.ic_search),
                tint = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentDescription = "search-icon"
            )
        }
    }
}