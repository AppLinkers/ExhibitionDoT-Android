package com.exhibitiondot.presentation.ui.screen.sign.signIn

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.exhibitiondot.domain.repository.UserRepository
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.model.KakaoAuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val kakaoAuthClient: KakaoAuthClient,
    private val uiModel: GlobalUiModel
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<SignInUiState>(SignInUiState.Nothing)
    val uiState: StateFlow<SignInUiState> = _uiState

    fun onKakaoLogin(
        context: Context,
        moveMain: () -> Unit,
        moveSignUp: () -> Unit
    ) {
        _uiState.update { SignInUiState.Loading }
        kakaoAuthClient.loginWithKakao(
            context = context,
            onSuccess = { signIn(moveMain, moveSignUp) },
            onFailure = { onFailSignIn() }
        )
    }

    private fun signIn(moveMain: () -> Unit, moveSignUp: () -> Unit) {
        val email = kakaoAuthClient.getUserEmail()
        if (email != null) {
            viewModelScope.launch {
                userRepository.signIn(email)
                    .onSuccess {
                        cacheUser(moveMain)
                    }.onFailure {
                        moveSignUp()
                    }
            }
        } else {
            onFailSignIn()
        }
    }

    private suspend fun cacheUser(
        moveMain: () -> Unit
    ) = userRepository.cacheUser()
            .onSuccess {
                moveMain()
            }.onFailure {
                onFailSignIn()
            }


    private fun onFailSignIn() {
        _uiState.update { SignInUiState.Nothing }
        uiModel.showToast("로그인에 실패했어요")
    }
}