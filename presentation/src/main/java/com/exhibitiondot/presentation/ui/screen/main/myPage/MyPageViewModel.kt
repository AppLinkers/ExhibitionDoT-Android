package com.exhibitiondot.presentation.ui.screen.main.myPage

import androidx.lifecycle.viewModelScope
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.usecase.user.GetCachedUserUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.mapper.toUiModel
import com.exhibitiondot.presentation.model.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    getCachedUserUseCase: GetCachedUserUseCase
) : BaseViewModel() {
    val user: StateFlow<UserUiModel> =
        getCachedUserUseCase()
            .map(User::toUiModel)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UserUiModel("","","",",","","","")
            )
}