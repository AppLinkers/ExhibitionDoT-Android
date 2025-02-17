package com.exhibitiondot.presentation.ui.screen

import com.exhibitiondot.domain.repository.PreferenceRepository
import com.exhibitiondot.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    preferenceRepository: PreferenceRepository,
) : BaseViewModel() {
    val authFlow: Flow<Boolean> = preferenceRepository.userId.map { it != null }
}