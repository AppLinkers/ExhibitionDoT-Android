package com.exhibitiondot.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exhibitiondot.domain.model.Filter
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.state.IMultiFilerState
import com.exhibitiondot.presentation.ui.state.ISingleFilterState
import com.exhibitiondot.presentation.ui.theme.screenPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DoTBottomSheet(
    showSheet: Boolean,
    containerColor: Color = MaterialTheme.colorScheme.background,
    sheetState: SheetState,
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    onDismissRequest: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            containerColor = containerColor,
            sheetState = sheetState,
            contentWindowInsets = {
                WindowInsets(
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                )
            },
            dragHandle = null,
            properties = properties,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeFilterSheet(
    title: String,
    scope: CoroutineScope,
    onApplyFilter: () -> Unit,
    onDismissRequest: () -> Unit,
    filterSelectScreen: @Composable () -> Unit
) {
    val showSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    DoTBottomSheet(
        showSheet = true,
        sheetState = showSheet,
        onDismissRequest = onDismissRequest
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = title,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Column(
            modifier = Modifier.padding(all = screenPadding)
        ) {
            filterSelectScreen()
            DoTSpacer(size = 50)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HomeSheetButton(
                    text = stringResource(R.string.close),
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    borderColor = MaterialTheme.colorScheme.onSurface,
                    onClick = {
                        scope.launch { showSheet.hide() }
                            .invokeOnCompletion { onDismissRequest() }
                    }
                )
                DoTSpacer(size = 10)
                HomeSheetButton(
                    text = stringResource(R.string.apply),
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background,
                    onClick = {
                        scope.launch {
                            onApplyFilter()
                            showSheet.hide()
                        }.invokeOnCompletion { onDismissRequest() }
                    }
                )
            }
        }
    }
}

@Composable
fun <T : Filter.SingleFilter> HomeSingleFilterSheet(
    title: String,
    scope: CoroutineScope,
    filterState: ISingleFilterState<T>,
    onApplyFilter: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    HomeFilterSheet(
        title = title,
        scope = scope,
        onApplyFilter = onApplyFilter,
        onDismissRequest = onDismissRequest
    ) {
        SingleFilterSelectScreen(
            filterState = filterState,
            needEntire = true,
        )
    }
}

@Composable
fun <T : Filter.MultiFilter> HomeMultiFilterSheet(
    title: String,
    scope: CoroutineScope,
    filterState: IMultiFilerState<T>,
    onApplyFilter: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    HomeFilterSheet(
        title = title,
        scope = scope,
        onApplyFilter = onApplyFilter,
        onDismissRequest = onDismissRequest
    ) {
        MultiFilterSelectScreen(
            filterState = filterState,
            needEntire = true,
        )
    }
}