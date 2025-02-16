package com.exhibitiondot.presentation.ui.screen.main.postEvent

sealed interface PostEventUiState {
    data object Idle : PostEventUiState
    data object Loading : PostEventUiState
    data object ShowDatePicker : PostEventUiState
}