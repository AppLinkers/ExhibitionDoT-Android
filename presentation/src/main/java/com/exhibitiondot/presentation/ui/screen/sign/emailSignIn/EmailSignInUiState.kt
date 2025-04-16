package com.exhibitiondot.presentation.ui.screen.sign.emailSignIn

sealed interface EmailSignInUiState {
    data object Idle : EmailSignInUiState
    data object Loading : EmailSignInUiState
}