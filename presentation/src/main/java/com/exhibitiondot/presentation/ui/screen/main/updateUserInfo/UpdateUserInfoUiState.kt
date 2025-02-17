package com.exhibitiondot.presentation.ui.screen.main.updateUserInfo

sealed interface UpdateUserInfoUiState {
    data object Idle : UpdateUserInfoUiState

    data object PostLoading : UpdateUserInfoUiState

    data object Loading : UpdateUserInfoUiState
}