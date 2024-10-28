package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.repository.UserRepository
import javax.inject.Inject

class CacheUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<Unit> = userRepository.cacheUser()
}