package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.model.User
import javax.inject.Inject

class SignUpAndSignInUseCase @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
) {
    suspend operator fun invoke(user: User): Result<Unit> = runCatching {
        signUpUseCase(user).getOrThrow()
        signInUseCase(user.email).getOrThrow()
    }
}