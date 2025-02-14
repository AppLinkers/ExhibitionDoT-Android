package com.exhibitiondot.presentation.ui.screen.sign.signUp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Filter
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.usecase.user.SignInUseCase
import com.exhibitiondot.domain.usecase.user.SignUpUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.ui.navigation.SignScreen
import com.exhibitiondot.presentation.ui.state.EditTextState
import com.exhibitiondot.presentation.ui.state.MultiFilterState
import com.exhibitiondot.presentation.ui.state.PhoneEditTextState
import com.exhibitiondot.presentation.ui.state.SingleFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val uiModel: GlobalUiModel,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val email = savedStateHandle.toRoute<SignScreen.SignUp>().email

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Nothing)
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _currentStep = MutableStateFlow<SignUpStep>(SignUpStep.InfoStep)
    val currentStep: StateFlow<SignUpStep> = _currentStep.asStateFlow()

    val nameState = EditTextState(maxLength = 10)
    val nicknameState = EditTextState(maxLength = 10)
    val phoneState = PhoneEditTextState()

    val regionState = SingleFilterState(
        initFilter = Region.Seoul,
        filterList = Region.values()
    )
    val categoryState = MultiFilterState(
        filterList = Category.values()
    )
    val eventTypeState = MultiFilterState(
        filterList = EventType.values()
    )

    fun onPrevStep(currentStep: SignUpStep, onBack: () -> Unit) {
        val prevStep = currentStep.prevStep()
        if (prevStep != null) {
            _currentStep.update { prevStep }
        } else {
            onBack()
        }
    }

    fun onNextStep(currentStep: SignUpStep, moveMain: () -> Unit, onBack: () -> Unit) {
        val nextStep = currentStep.nextStep()
        if (nextStep != null) {
            _currentStep.update { nextStep }
        } else {
            signUp(moveMain, onBack)
        }
    }

    fun validate(): Boolean {
        return when (currentStep.value) {
            SignUpStep.InfoStep -> {
                nameState.isValidate() && nicknameState.isValidate() && phoneState.isValidate()
            }
            else -> true
        }
    }

    private fun signUp(moveMain: () -> Unit, onBack: () -> Unit) {
        _uiState.update { SignUpUiState.Loading }
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
            signUpUseCase(user)
                .onSuccess {
                    signIn(moveMain, onBack)
                }.onFailure {
                    _uiState.update { SignUpUiState.Nothing }
                    uiModel.showToast("회원가입에 실패했어요")
                }
        }
    }

    private suspend fun signIn(moveMain: () -> Unit, onBack: () -> Unit) =
        signInUseCase(email)
            .onSuccess {
                moveMain()
            }.onFailure {
                onBack()
            }
}