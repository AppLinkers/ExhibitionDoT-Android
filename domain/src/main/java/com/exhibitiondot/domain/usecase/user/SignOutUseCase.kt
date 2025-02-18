package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.PreferenceRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke() {
        preferenceRepository.updateUserId(null)
        preferenceRepository.updateCurrentUser(User.NONE)
    }
}