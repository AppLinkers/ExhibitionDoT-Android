package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.UserRepository
import javax.inject.Inject

class GetCachedUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): User = userRepository.getUser()
}