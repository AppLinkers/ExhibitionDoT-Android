package com.exhibitiondot.presentation.ui.screen.main.myPage

import com.exhibitiondot.presentation.model.UserUiModel

sealed interface MyPageUiState {
    data object Loading : MyPageUiState

    data class Success(val user: UserUiModel) : MyPageUiState
}