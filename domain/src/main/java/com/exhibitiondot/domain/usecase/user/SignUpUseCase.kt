package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.exception.SignUpFailException
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.PreferenceRepository
import com.exhibitiondot.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(user: User): Result<Unit> {
        userRepository.signUp(user)
            .onSuccess {
                preferenceRepository.updateCurrentUser(user)
                return Result.success(it)
            }
            .onFailure { t ->
                return Result.failure(SignUpFailException(t))
            }
        return Result.failure(IllegalStateException())
    }
}