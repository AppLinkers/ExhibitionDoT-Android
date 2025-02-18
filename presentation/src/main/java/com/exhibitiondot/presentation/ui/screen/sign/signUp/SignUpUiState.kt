package com.exhibitiondot.presentation.ui.screen.sign.signUp

sealed interface SignUpUiState {
    data object Idle : SignUpUiState

    data object Loading : SignUpUiState
}