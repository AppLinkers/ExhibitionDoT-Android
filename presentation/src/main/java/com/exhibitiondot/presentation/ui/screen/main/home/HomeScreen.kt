package com.exhibitiondot.presentation.ui.screen.main.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
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
import com.exhibitiondot.presentation.ui.component.DoTImage
import com.exhibitiondot.presentation.ui.component.DoTLoadingScreen
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DownIcon
import com.exhibitiondot.presentation.ui.component.HeartIcon
import com.exhibitiondot.presentation.ui.component.HomeFilterChip
import com.exhibitiondot.presentation.ui.component.HomeFilterSheet
import com.exhibitiondot.presentation.ui.component.HomeSearchDialog
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
    moveMy: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val eventParams by viewModel.eventParams.collectAsStateWithLifecycle()
    val eventList = viewModel.eventList.collectAsLazyPagingItems()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedRegion by viewModel.selectedRegion.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedEventType by viewModel.selectedEventType.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        eventParams = eventParams,
        eventList = eventList,
        resetAllFilters = viewModel::resetAllFilters,
        resetAppliedQuery = viewModel::resetAppliedQuery,
        showFilterSheet = viewModel::showFilterSheet,
        showSearchDialog = viewModel::showSearchDialog,
        onEventItem = moveEventDetail,
        moveMy = moveMy
    )
    if (uiState is HomeUiState.FilterState) {
        val filterState = uiState as HomeUiState.FilterState
        HomeFilterSheet(
            title = stringResource(filterState.title),
            scope = scope,
            filerList = viewModel.getFilterList(filterState),
            selectedFilter = when (filterState) {
                HomeUiState.FilterState.ShowRegionFilter -> selectedRegion
                else -> null
            },
            selectedFilterList = when (filterState) {
                HomeUiState.FilterState.ShowCategoryFilter -> selectedCategory
                HomeUiState.FilterState.ShowEventTypeFilter -> selectedEventType
                else -> emptyList()
            },
            onSelectFilter = { filter -> viewModel.selectFilter(filterState, filter) },
            onSelectAll = { viewModel.selectAll(filterState) },
            onApplyFilter = { viewModel.applyFilters(filterState) },
            onDismissRequest = viewModel::dismiss
        )
    }
    if (uiState is HomeUiState.ShowSearchDialog) {
        HomeSearchDialog(
            queryState = viewModel.queryState,
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
    showFilterSheet: (HomeUiState.FilterState) -> Unit,
    showSearchDialog: () -> Unit,
    onEventItem: (Long) -> Unit,
    moveMy: () -> Unit,
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
                showFilterSheet = showFilterSheet
            )
        }
        when (eventList.loadState.refresh) {
            LoadState.Loading -> DoTLoadingScreen(
                modifier = Modifier.weight(1f)
            )
            is LoadState.Error -> {}
            is LoadState.NotLoading -> EventList(
                modifier = Modifier.weight(1f),
                lazyGridState = lazyGridState,
                eventList = eventList,
                onEventItem = onEventItem
            )
        }
    }
}

@Composable
private fun HomeFilterList(
    modifier: Modifier = Modifier,
    eventParams: EventParams,
    resetAllFilters: () -> Unit,
    resetAppliedQuery: () -> Unit,
    showFilterSheet: (HomeUiState.FilterState) -> Unit,
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
            text = eventParams.reginText(default = stringResource(R.string.region)),
            isApplied = eventParams.region != null,
            onClick = { showFilterSheet(HomeUiState.FilterState.ShowRegionFilter) },
            trailingIcon = {
                DownIcon(size = 20)
            }
        )
        HomeFilterChip(
            text = eventParams.categoryText(default = stringResource(R.string.category)),
            isApplied = eventParams.categoryList.isNotEmpty(),
            onClick = { showFilterSheet(HomeUiState.FilterState.ShowCategoryFilter) },
            trailingIcon = {
                DownIcon(size = 20)
            }
        )
        HomeFilterChip(
            text = eventParams.eventTypeText(default = stringResource(R.string.event_type)),
            isApplied = eventParams.eventTypeList.isNotEmpty(),
            onClick = { showFilterSheet(HomeUiState.FilterState.ShowEventTypeFilter) },
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
        contentPadding = PaddingValues(horizontal = screenPadding),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
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
        item {
            DoTSpacer(size = 800)
        }
    }
}

@Composable
private fun EventItem(
    modifier: Modifier = Modifier,
    event: EventUiModel,
    onEventItem: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onEventItem)
    ) {
        DoTImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(MaterialTheme.shapes.medium),
            url = "https://www.it-b.co.kr/news/photo/202011/45197_42822_152.png",
            contentScale = ContentScale.FillBounds
        )
        DoTSpacer(size = 10)
        Text(
            text = event.date,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
        DoTSpacer(size = 6)
        Text(
            modifier = Modifier.height(50.dp),
            text = "2024 Naver Corp. 컨퍼런스 (코엑스 컨벤션 홀)",
            style = MaterialTheme.typography.labelMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        DoTSpacer(size = 12)
        if (event.likeCount != 0) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                HeartIcon(
                    size = 16,
                    isLike = true
                )
                DoTSpacer(size = 4)
                Text(
                    text = "${event.likeCount}",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}