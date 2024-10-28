package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(email: String): Result<Unit> = userRepository.signIn(email)
}