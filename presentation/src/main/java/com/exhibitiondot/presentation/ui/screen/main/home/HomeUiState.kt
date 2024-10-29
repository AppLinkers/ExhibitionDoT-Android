package com.exhibitiondot.presentation.ui.screen.main.home

sealed interface HomeUiState {
    data object Nothing : HomeUiState
    data object ShowSearchDialog : HomeUiState

    sealed interface FilterState : HomeUiState {
        data object ShowRegionFilter: FilterState
        data object ShowCategoryFilter : FilterState
        data object ShowEventTypeFilter : FilterState
    }
}