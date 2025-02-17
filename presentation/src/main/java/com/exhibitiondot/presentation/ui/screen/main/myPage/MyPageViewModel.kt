package com.exhibitiondot.presentation.ui.screen.main.myPage

import androidx.lifecycle.viewModelScope
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.usecase.user.GetCacheFirstUserFlowUseCase
import com.exhibitiondot.domain.usecase.user.SignOutUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.mapper.toUiModel
import com.exhibitiondot.presentation.model.KakaoAuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    getCacheFirstUserFlowUseCase: GetCacheFirstUserFlowUseCase,
    private val kakaoAuthClient: KakaoAuthClient,
) : BaseViewModel() {
    val uiState: StateFlow<MyPageUiState> =
        getCacheFirstUserFlowUseCase()
            .map(User::toUiModel)
            .map(MyPageUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MyPageUiState.Loading
            )

    fun signOut() {
        viewModelScope.launch {
            kakaoAuthClient.logOut()
            signOutUseCase()
        }
    }
}