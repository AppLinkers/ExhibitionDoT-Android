package com.exhibitiondot.presentation.ui.screen.main.home

import androidx.annotation.StringRes
import com.exhibitiondot.presentation.R

sealed interface HomeUiState {
    data object Nothing : HomeUiState
    data object ShowSearchDialog : HomeUiState

    sealed class FilterState(@StringRes val title: Int) : HomeUiState {
        data object ShowRegionFilter: FilterState(R.string.home_select_region)
        data object ShowCategoryFilter : FilterState(R.string.home_select_category)
        data object ShowEventTypeFilter : FilterState(R.string.home_select_event_type)
    }
}