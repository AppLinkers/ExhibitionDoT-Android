package com.exhibitiondot.presentation.ui.screen.sign.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.exhibitiondot.domain.exception.NetworkFailException
import com.exhibitiondot.domain.exception.SignInFailException
import com.exhibitiondot.domain.exception.SignUpFailException
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.usecase.user.SignUpAndSignInUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.mapper.getMessage
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.ui.navigation.SignScreen
import com.exhibitiondot.presentation.ui.state.EditTextState
import com.exhibitiondot.presentation.ui.state.MultiFilterState
import com.exhibitiondot.presentation.ui.state.PhoneEditTextState
import com.exhibitiondot.presentation.ui.state.SingleFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpAndSignInUseCase: SignUpAndSignInUseCase,
    private val uiModel: GlobalUiModel,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val email = savedStateHandle.toRoute<SignScreen.SignUp>().email

    var uiState by mutableStateOf<SignUpUiState>(SignUpUiState.Idle)
        private set
    var currentStep by mutableStateOf(SignUpStep.InfoStep)
        private set
    private val totalSteps = SignUpStep.entries

    val nameState = EditTextState(maxLength = 10)
    val nicknameState = EditTextState(maxLength = 10)
    val phoneState = PhoneEditTextState()

    val regionState = SingleFilterState(Region.entries)
    val categoryState = MultiFilterState(Category.entries)
    val eventTypeState = MultiFilterState(EventType.entries)

    fun onPrevStep(onBack: () -> Unit) {
        val prevIdx = totalSteps.indexOf(currentStep) - 1
        if (prevIdx < 0) {
            onBack()
        } else {
            currentStep = totalSteps[prevIdx]
        }
    }

    fun onNextStep(onBack: () -> Unit) {
        val nextIdx = totalSteps.indexOf(currentStep) + 1
        if (nextIdx > totalSteps.lastIndex) {
            signUp(onBack)
        } else {
            currentStep = totalSteps[nextIdx]
        }
    }

    fun lastStep(): Boolean {
        return currentStep == totalSteps.last()
    }

    fun validate(): Boolean {
        return when (currentStep) {
            SignUpStep.InfoStep -> {
                nameState.isValidate() && nicknameState.isValidate() && phoneState.isValidate()
            }
            SignUpStep.RegionStep -> regionState.selectedFilter != null
            else -> true
        }
    }

    private fun signUp(onBack: () -> Unit) {
        uiState = SignUpUiState.Loading
        viewModelScope.launch {
            val user = User(
                email = email,
                name = nameState.trimmedText(),
                nickname = nicknameState.trimmedText(),
                phone = phoneState.trimmedText(),
                region = regionState.selectedFilter!!,
                categoryList = categoryState.selectedFilterList,
                eventTypeList = eventTypeState.selectedFilterList
            )
            signUpAndSignInUseCase(user)
                .onFailure { t ->
                    when (t) {
                        is SignUpFailException, is NetworkFailException -> {
                            val msg = t.getMessage("회원가입에 실패했어요")
                            onFailure(msg)
                        }
                        is SignInFailException -> {
                            val msg = t.getMessage("다시 로그인 해주세요")
                            onFailure(msg)
                            onBack()
                        }
                    }
                }
        }
    }

    private fun onFailure(msg: String) {
        uiState = SignUpUiState.Idle
        uiModel.showToast(msg)
    }
}