package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.exception.SignInFailException
import com.exhibitiondot.domain.repository.PreferenceRepository
import com.exhibitiondot.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(email: String): Result<Unit> = runCatching {
        val userId = userRepository.signIn(email)
            .getOrElse { exception -> throw SignInFailException(exception) }
        preferenceRepository.updateUserId(userId)
    }
}