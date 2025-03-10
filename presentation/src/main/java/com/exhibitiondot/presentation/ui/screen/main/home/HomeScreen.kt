package com.exhibitiondot.presentation.ui.screen.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.exhibitiondot.domain.model.EventParams
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.model.EventUiModel
import com.exhibitiondot.presentation.ui.component.DoTEmptyScreen
import com.exhibitiondot.presentation.ui.component.DoTImage
import com.exhibitiondot.presentation.ui.component.DoTLoadingScreen
import com.exhibitiondot.presentation.ui.component.DoTRefreshScreen
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DownIcon
import com.exhibitiondot.presentation.ui.component.HomeAddButton
import com.exhibitiondot.presentation.ui.component.HomeFilterChip
import com.exhibitiondot.presentation.ui.component.HomeMultiFilterSheet
import com.exhibitiondot.presentation.ui.component.HomeSearchDialog
import com.exhibitiondot.presentation.ui.component.HomeSingleFilterSheet
import com.exhibitiondot.presentation.ui.component.HomeTopBar
import com.exhibitiondot.presentation.ui.component.RedoIcon
import com.exhibitiondot.presentation.ui.component.XIcon
import com.exhibitiondot.presentation.ui.theme.screenPadding
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    moveEventDetail: (Long) -> Unit,
    movePostEvent: (Long?) -> Unit,
    moveMy: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val eventParams by viewModel.eventParams.collectAsStateWithLifecycle()
    val eventList = viewModel.eventList.collectAsLazyPagingItems()
    val homeUpdateFlag by viewModel.flagModel.homeUpdateFlag.collectAsStateWithLifecycle()

    LaunchedEffect(homeUpdateFlag) {
        if (homeUpdateFlag) {
            eventList.refresh()
            viewModel.flagModel.setHomeUpdateFlag(false)
        }
    }

    HomeScreen(
        modifier = modifier,
        eventParams = eventParams,
        eventList = eventList,
        resetAllFilters = viewModel::resetAllFilters,
        resetAppliedQuery = viewModel::resetAppliedQuery,
        showSearchDialog = viewModel::showSearchDialog,
        showRegionFilter = viewModel::showRegionFilter,
        showCategoryFilter = viewModel::showCategoryFilter,
        showEventTypeFilter = viewModel::showEventTypeFilter,
        onEventItem = moveEventDetail,
        movePostEvent = { movePostEvent(null) },
        moveMy = moveMy
    )
    if (uiState is HomeUiState.ShowRegionFilter) {
        HomeSingleFilterSheet(
            title = stringResource(R.string.home_select_region),
            scope = scope,
            filterState = viewModel.regionState,
            onApplyFilter = viewModel::applyRegionFilter,
            onDismissRequest = viewModel::dismiss
        )
    }
    if (uiState is HomeUiState.ShowCategoryFilter) {
        HomeMultiFilterSheet(
            title = stringResource(R.string.home_select_category),
            scope = scope,
            filterState = viewModel.categoryState,
            onApplyFilter = viewModel::applyCategoryFilter,
            onDismissRequest = viewModel::dismiss
        )
    }
    if (uiState is HomeUiState.ShowEventTypeFilter) {
        HomeMultiFilterSheet(
            title = stringResource(R.string.home_select_event_type),
            scope = scope,
            filterState = viewModel.eventTypeState,
            onApplyFilter = viewModel::applyEventTypeFilter,
            onDismissRequest = viewModel::dismiss
        )
    }
    if (uiState is HomeUiState.ShowSearchDialog) {
        HomeSearchDialog(
            query = uiState.query,
            applyQuery = viewModel::applyQuery,
            onDismissRequest = viewModel::dismiss
        )
    }
}

@Composable
private fun HomeScreen(
    modifier: Modifier,
    eventParams: EventParams,
    eventList: LazyPagingItems<EventUiModel>,
    resetAllFilters: () -> Unit,
    resetAppliedQuery: () -> Unit,
    showSearchDialog: () -> Unit,
    showRegionFilter: () -> Unit,
    showCategoryFilter: () -> Unit,
    showEventTypeFilter: () -> Unit,
    onEventItem: (Long) -> Unit,
    movePostEvent: () -> Unit,
    moveMy: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            val lazyGridState = rememberLazyGridState()
            val shadowVisible by remember { derivedStateOf { lazyGridState.firstVisibleItemScrollOffset > 0 } }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = if (shadowVisible) 0.5.dp else 0.dp
                    )
            ) {
                HomeTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    showSearchDialog = showSearchDialog,
                    moveMy = moveMy
                )
                HomeFilterList(
                    eventParams = eventParams,
                    resetAllFilters = resetAllFilters,
                    resetAppliedQuery = resetAppliedQuery,
                    showRegionFilter = showRegionFilter,
                    showCategoryFilter = showCategoryFilter,
                    showEventTypeFilter = showEventTypeFilter,
                )
            }
            when (eventList.loadState.refresh) {
                LoadState.Loading -> DoTLoadingScreen(
                    modifier = Modifier.weight(1f)
                )
                is LoadState.Error -> DoTRefreshScreen(
                    modifier = Modifier.weight(1f),
                    onRefresh = eventList::refresh
                )
                is LoadState.NotLoading -> {
                    if (eventList.itemCount == 0) {
                        DoTEmptyScreen(
                            modifier = Modifier.weight(1f),
                            description = stringResource(R.string.home_empty_description)
                        )
                    } else {
                        EventList(
                            modifier = Modifier.weight(1f),
                            lazyGridState = lazyGridState,
                            eventList = eventList,
                            onEventItem = onEventItem
                        )
                    }
                }
            }
        }
        HomeAddButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = screenPadding),
            onClick = movePostEvent
        )
    }
}

@Composable
private fun HomeFilterList(
    modifier: Modifier = Modifier,
    eventParams: EventParams,
    resetAllFilters: () -> Unit,
    resetAppliedQuery: () -> Unit,
    showRegionFilter: () -> Unit,
    showCategoryFilter: () -> Unit,
    showEventTypeFilter: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = screenPadding,
                bottom = 16.dp
            )
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (eventParams.canReset()) {
            HomeFilterChip(
                text = stringResource(R.string.reset),
                isApplied = false,
                onClick = resetAllFilters,
                leadingIcon = {
                    RedoIcon(
                        modifier = Modifier.padding(end = 4.dp),
                        size = 16
                    )
                }
            )
        }
        if (eventParams.query.isNotEmpty()) {
            HomeFilterChip(
                text = eventParams.query,
                isApplied = true,
                onClick = resetAppliedQuery,
                trailingIcon = {
                    XIcon(
                        modifier = Modifier.padding(start = 4.dp),
                        size = 14
                    )
                }
            )
        }
        HomeFilterChip(
            text = eventParams.regionText(default = stringResource(R.string.region)),
            isApplied = eventParams.region != null,
            onClick = showRegionFilter,
            trailingIcon = {
                DownIcon(size = 20)
            }
        )
        HomeFilterChip(
            text = eventParams.categoryText(default = stringResource(R.string.category)),
            isApplied = eventParams.categoryList.isNotEmpty(),
            onClick = showCategoryFilter,
            trailingIcon = {
                DownIcon(size = 20)
            }
        )
        HomeFilterChip(
            text = eventParams.eventTypeText(default = stringResource(R.string.event_type)),
            isApplied = eventParams.eventTypeList.isNotEmpty(),
            onClick = showEventTypeFilter,
            trailingIcon = {
                DownIcon(size = 20)
            }
        )
        DoTSpacer(size = 20)
    }
}

@Composable
private fun EventList(
    modifier: Modifier = Modifier,
    lazyGridState: LazyGridState,
    eventList: LazyPagingItems<EventUiModel>,
    onEventItem: (Long) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        state = lazyGridState,
    ) {
        items(
            count = eventList.itemCount,
            key = eventList.itemKey { it.id }
        ) { index ->
            eventList[index]?.let { event ->
                EventItem(
                    event = event,
                    onEventItem = { onEventItem(event.id) }
                )
            }
        }
    }
}

@Composable
private fun EventItem(
    modifier: Modifier = Modifier,
    event: EventUiModel,
    onEventItem: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 3 / 4f)
            .clickable(onClick = onEventItem)
    ) {
        DoTImage(
            modifier = Modifier.fillMaxSize(),
            url = event.imgUrl,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.1f),
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.5f),
                        )
                    )
                )
                .padding(
                    vertical = screenPadding,
                    horizontal = 10.dp
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = event.date,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                DoTSpacer(size = 6)
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.background,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}