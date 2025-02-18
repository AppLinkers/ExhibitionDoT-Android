package com.exhibitiondot.presentation.ui.screen.sign.signIn

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.exhibitiondot.domain.usecase.user.SignInUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.model.KakaoAuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val kakaoAuthClient: KakaoAuthClient,
    private val uiModel: GlobalUiModel
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<SignInUiState>(SignInUiState.Nothing)
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onKakaoLogin(
        context: Context,
        moveSignUp: (String) -> Unit
    ) {
        _uiState.update { SignInUiState.Loading }
        kakaoAuthClient.loginWithKakao(
            context = context,
            onSuccess = { getEmailFromAccount(moveSignUp) },
            onFailure = { onFailSignIn("카카오 로그인에 실패했어요") }
        )
    }

    private fun getEmailFromAccount(moveSignUp: (String) -> Unit) {
        kakaoAuthClient.getUserEmail(
            onSuccess = { email ->
                signIn(email, moveSignUp)
            },
            onFailure = {
                onFailSignIn("다시 시도해주세요")
            }
        )
    }

    private fun signIn(email: String, moveSignUp: (String) -> Unit) {
        viewModelScope.launch {
            signInUseCase(email)
                .onFailure {
                    moveSignUp(email)
                }
            _uiState.update { SignInUiState.Nothing }
        }
    }

    private fun onFailSignIn(msg: String) {
        _uiState.update { SignInUiState.Nothing }
        uiModel.showToast(msg)
    }
}