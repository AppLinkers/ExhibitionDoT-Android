package com.exhibitiondot.presentation.ui.screen.main.eventDetail

import com.exhibitiondot.presentation.model.EventDetailUiModel

sealed interface EventDetailUiState {
    data object Loading : EventDetailUiState

    data object Failure : EventDetailUiState

    data class Success(
        val eventDetail : EventDetailUiModel
    ) : EventDetailUiState
}

sealed interface EventDetailDialogState {
    data object Nothing : EventDetailDialogState

    data object ShowReportDialog : EventDetailDialogState

    data object ShowUpdateDeleteDialog : EventDetailDialogState
}