package com.exhibitiondot.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Filter
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.state.IMultiFilerState
import com.exhibitiondot.presentation.ui.state.ISingleFilterState

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterSelectScreen(
    modifier: Modifier = Modifier,
    content: @Composable FlowRowScope.() -> Unit
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = content
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T : Filter.SingleFilter> SingleFilterSelectScreen(
    modifier: Modifier = Modifier,
    filterState: ISingleFilterState<T>,
    needEntire: Boolean = false,
) {
    FilterSelectScreen(
        modifier = modifier
    ) {
        if (needEntire) {
            DoTFilterChip(
                name = stringResource(R.string.entire),
                selected = filterState.selectedFilter == null,
                onSelect = filterState::resetAll
            )
        }
        filterState.filterList.forEach { filter ->
            DoTFilterChip(
                name = when (filter) {
                    is Region -> filter.name
                    else -> ""
                },
                selected = filter == filterState.selectedFilter,
                onSelect = { filterState.selectFilter(filter) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T : Filter.MultiFilter> MultiFilterSelectScreen(
    modifier: Modifier = Modifier,
    filterState: IMultiFilerState<T>,
    needEntire: Boolean = false,
) {
    FilterSelectScreen(
        modifier = modifier
    ) {
        if (needEntire) {
            DoTFilterChip(
                name = stringResource(R.string.entire),
                selected = filterState.selectedFilterList.isEmpty(),
                onSelect = filterState::resetAll
            )
        }
        filterState.filterList.forEach { filter ->
            DoTFilterChip(
                name = when (filter) {
                    is Category -> filter.key
                    is EventType -> filter.key
                    else -> ""
                },
                selected = filter in filterState.selectedFilterList,
                onSelect = { filterState.selectFilter(filter) }
            )
        }
    }
}