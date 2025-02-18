package com.exhibitiondot.presentation.ui.screen.main.home

sealed interface HomeUiState {
    data object Idle : HomeUiState

    data class ShowSearchDialog(val query: String) : HomeUiState

    data object ShowRegionFilter : HomeUiState

    data object ShowCategoryFilter : HomeUiState

    data object ShowEventTypeFilter : HomeUiState
}