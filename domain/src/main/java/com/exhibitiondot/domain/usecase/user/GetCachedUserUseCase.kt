package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCachedUserUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
) {
    operator fun invoke(): StateFlow<User> = preferenceRepository.currentUser
}