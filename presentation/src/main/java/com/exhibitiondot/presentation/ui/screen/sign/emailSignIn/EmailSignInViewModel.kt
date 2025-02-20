package com.exhibitiondot.presentation.ui.screen.sign.emailSignIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.exhibitiondot.domain.usecase.user.SignInUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.mapper.getMessage
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.ui.state.EmailEditTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailSignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val uiModel: GlobalUiModel,
) : BaseViewModel() {
    var uiState by mutableStateOf<EmailSignInUiState>(EmailSignInUiState.Idle)
    val emailState = EmailEditTextState()

    fun signIn() {
        uiState = EmailSignInUiState.Loading
        viewModelScope.launch {
            signInUseCase(emailState.trimmedText())
                .onFailure { t ->
                    val msg = t.getMessage("로그인에 실패했어요")
                    uiModel.showToast(msg)
                    uiState = EmailSignInUiState.Idle
                }
        }
    }
}