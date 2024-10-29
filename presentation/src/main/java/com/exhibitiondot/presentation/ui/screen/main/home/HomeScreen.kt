package com.exhibitiondot.presentation.ui.screen.main.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.presentation.ui.component.HomeTopBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    moveEventDetail: (Long) -> Unit,
    moveMy: () -> Unit,
    onBack: () -> Unit,
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
        if (uiState == HomeUiState.Nothing) {
            onBack()
        } else {
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
    eventList: LazyPagingItems<Event>,
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
        HomeTopBar(
            showSearchDialog = showSearchDialog,
            moveMy = moveMy
        )
    }
}