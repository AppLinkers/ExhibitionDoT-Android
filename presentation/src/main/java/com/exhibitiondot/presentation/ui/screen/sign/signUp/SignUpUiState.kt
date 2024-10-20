package com.exhibitiondot.presentation.ui.screen.sign.signUp

sealed interface SignUpUiState {
    data object Nothing : SignUpUiState
    data object Loading : SignUpUiState
}