package com.exhibitiondot.presentation.ui.screen.sign.signIn

sealed interface SignInUiState {
    data object Nothing : SignInUiState
    data object Loading : SignInUiState
}