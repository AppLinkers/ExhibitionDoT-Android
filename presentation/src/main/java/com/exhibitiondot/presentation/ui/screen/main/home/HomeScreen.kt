package com.exhibitiondot.presentation.ui.screen.main.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.presentation.model.EventUiModel
import com.exhibitiondot.presentation.ui.component.DoTImage
import com.exhibitiondot.presentation.ui.component.DoTLoadingScreen
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.HeartIcon
import com.exhibitiondot.presentation.ui.component.HomeTopBar
import com.exhibitiondot.presentation.ui.theme.screenPadding
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    moveEventDetail: (Long) -> Unit,
    moveMy: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val appliedRegion by viewModel.appliedRegion.collectAsStateWithLifecycle()
    val appliedCategory by viewModel.appliedCategory.collectAsStateWithLifecycle()
    val appliedEventType by viewModel.appliedEventType.collectAsStateWithLifecycle()
    val appliedQuery by viewModel.appliedQuery.collectAsStateWithLifecycle()
    val eventList = viewModel.eventList.collectAsLazyPagingItems()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedRegion by viewModel.selectedRegion.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedEventType by viewModel.selectedEventType.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        appliedRegion = appliedRegion,
        appliedCategory = appliedCategory,
        appliedEventType = appliedEventType,
        appliedQuery = appliedQuery,
        eventList = eventList,
        canResetFilters = viewModel.canResetFilters(),
        resetAllFilters = viewModel::resetAllFilters,
        resetAppliedQuery = viewModel::resetAppliedQuery,
        showFilterSheet = viewModel::showFilterSheet,
        showSearchDialog = viewModel::showSearchDialog,
        onEventItem = moveEventDetail,
        moveMy = moveMy
    )

    BackHandler {
        if (uiState != HomeUiState.Nothing) {
            viewModel.dismiss()
        }
    }
}

@Composable
private fun HomeScreen(
    modifier: Modifier,
    appliedRegion: Region?,
    appliedCategory: List<Category>,
    appliedEventType: List<EventType>,
    appliedQuery: String,
    eventList: LazyPagingItems<EventUiModel>,
    canResetFilters: Boolean,
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
        HomeTopBar(
            modifier = Modifier.fillMaxWidth(),
            showSearchDialog = showSearchDialog,
            moveMy = moveMy
        )
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
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            count = eventList.itemCount,
            key = { it }
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
            url = "https://www.it-b.co.kr/news/photo/202011/45197_42822_152.png"
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