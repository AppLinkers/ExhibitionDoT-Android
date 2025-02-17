package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.model.UpdateUserInfo
import com.exhibitiondot.domain.repository.PreferenceRepository
import com.exhibitiondot.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(updateUserInfo: UpdateUserInfo): Result<Unit> {
        userRepository.updateUserInfo(updateUserInfo)
            .onSuccess {
                preferenceRepository.updateCurrentUser(updateUserInfo)
                return Result.success(it)
            }
            .onFailure { t ->
                return Result.failure(t)
            }
        return Result.failure(IllegalStateException())
    }
}