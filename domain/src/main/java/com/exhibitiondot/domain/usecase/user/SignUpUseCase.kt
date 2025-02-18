package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.exception.NetworkFailException
import com.exhibitiondot.domain.exception.SignUpFailException
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.PreferenceRepository
import com.exhibitiondot.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(user: User): Result<Unit> = runCatching {
        userRepository.signUp(user)
            .getOrElse { exception ->
                when (exception) {
                    is NetworkFailException -> throw exception
                    else -> throw SignUpFailException(exception)
                }
            }
        preferenceRepository.updateCurrentUser(user)
    }
}