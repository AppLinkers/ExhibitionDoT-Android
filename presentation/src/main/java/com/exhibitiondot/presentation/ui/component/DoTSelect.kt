package com.exhibitiondot.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSelectScreen(
    modifier: Modifier = Modifier,
    filterList: List<Filter>,
    selectedFilter: Filter? = null,
    selectedFilterList: List<Filter> = emptyList(),
    onSelectFilter: (Filter) -> Unit,
    onSelectAll: (() -> Unit)? = null
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        onSelectAll?.let { selectAll ->
            DoTFilterChip(
                name = stringResource(R.string.entire),
                selected = when (filterList.first()) {
                    is Filter.SingleFilter -> selectedFilter == null
                    is Filter.MultiFilter -> selectedFilterList.isEmpty()
                },
                onSelect = selectAll
            )
        }
        filterList.forEach { filter ->
            DoTFilterChip(
                name = when (filter) {
                    is Region -> filter.name
                    is Category -> filter.key
                    is EventType -> filter.key
                    else -> ""
                },
                selected = when (filter) {
                    is Filter.SingleFilter -> filter == selectedFilter
                    is Filter.MultiFilter -> filter in selectedFilterList
                },
                onSelect = { onSelectFilter(filter) }
            )
        }
    }
}