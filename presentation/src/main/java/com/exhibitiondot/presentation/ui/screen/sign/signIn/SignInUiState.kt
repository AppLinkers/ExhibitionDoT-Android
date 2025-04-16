package com.exhibitiondot.presentation.ui.screen.sign.signIn

sealed interface SignInUiState {
    data object Idle : SignInUiState
    data object Loading : SignInUiState
}