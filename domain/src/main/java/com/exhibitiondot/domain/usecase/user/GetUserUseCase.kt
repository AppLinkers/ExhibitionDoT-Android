package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<User> = userRepository.getUser()
}