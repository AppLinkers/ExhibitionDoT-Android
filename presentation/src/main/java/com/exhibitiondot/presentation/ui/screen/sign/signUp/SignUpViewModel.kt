package com.exhibitiondot.presentation.ui.screen.sign.signUp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.usecase.user.SignInUseCase
import com.exhibitiondot.domain.usecase.user.SignUpUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.ui.navigation.KEY_SIGN_UP_EMAIL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val email: String = checkNotNull(savedStateHandle[KEY_SIGN_UP_EMAIL])

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Nothing)
    val uiState: StateFlow<SignUpUiState> = _uiState

    private val _currentStep = MutableStateFlow<SignUpStep>(SignUpStep.RegionStep)
    val currentStep: StateFlow<SignUpStep> = _currentStep

    private val _selectedRegion = MutableStateFlow<Region>(Region.Seoul)
    val selectedRegion: StateFlow<Region> = _selectedRegion

    private val _selectedCategory = MutableStateFlow<List<Category>>(emptyList())
    val selectedCategory: StateFlow<List<Category>> = _selectedCategory

    private val _selectedEventType = MutableStateFlow<List<EventType>>(emptyList())
    val selectedEventType: StateFlow<List<EventType>> = _selectedEventType

    fun onPrevStep(currentStep: SignUpStep, onBack: () -> Unit) {
        val prevStep = currentStep.onPrevStep()
        if (prevStep != null) {
            _currentStep.update { prevStep }
        } else {
            onBack()
        }
    }

    fun onNextStep(currentStep: SignUpStep, moveMain: () -> Unit, onBack: () -> Unit) {
        val nextStep = currentStep.onNextStep()
        if (nextStep != null) {

            _currentStep.update { nextStep }
        } else {
            signUp(moveMain, onBack)
        }
    }

    fun selectRegion(region: Region) {
        _selectedRegion.update { region }
    }

    fun selectCategory(category: Category) {
        if (category in selectedCategory.value) {
            _selectedCategory.update {
                selectedCategory.value.filter { it != category }
            }
        } else {
            _selectedCategory.update {
                selectedCategory.value + category
            }
        }
    }

    fun selectEventType(eventType: EventType) {
        if (eventType in selectedEventType.value) {
            _selectedEventType.update {
                selectedEventType.value.filter { it != eventType }
            }
        } else {
            _selectedEventType.update {
                selectedEventType.value + eventType
            }
        }
    }

    private fun signUp(moveMain: () -> Unit, onBack: () -> Unit) {
        _uiState.update { SignUpUiState.Loading }
        viewModelScope.launch {
            val user = User(
                email = email,
                name = "",
                phone = "",
                nickname = "",
                region = selectedRegion.value,
                categoryList = selectedCategory.value,
                eventTypeList = selectedEventType.value
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