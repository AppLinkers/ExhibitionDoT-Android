package com.exhibitiondot.presentation.ui.screen.main.eventDetail

import com.exhibitiondot.presentation.model.EventDetailUiModel

sealed interface EventDetailUiState {
    data object Loading : EventDetailUiState
    data class Success(
        val eventDetail : EventDetailUiModel
    ) : EventDetailUiState
}