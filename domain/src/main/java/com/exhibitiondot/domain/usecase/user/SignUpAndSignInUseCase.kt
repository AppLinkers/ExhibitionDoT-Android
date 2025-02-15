package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.model.User
import javax.inject.Inject

class SignUpAndSignInUseCase @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
) {
    suspend operator fun invoke(user: User): Result<Unit> {
        signUpUseCase(user)
            .onSuccess {
                signInUseCase(user.email)
                    .onSuccess {
                        return Result.success(it)
                    }
                    .onFailure { t ->
                        return Result.failure(t)
                    }
            }
            .onFailure { t ->
                return Result.failure(t)
            }
        return Result.failure(IllegalStateException())
    }
}