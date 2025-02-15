package com.exhibitiondot.domain.usecase.user

import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCacheFirstUserFlowUseCase @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val preferenceRepository: PreferenceRepository,
) {
    operator fun invoke(): Flow<User> = flow {
        if (preferenceRepository.currentUser.value == User.NONE) {
            getUserUseCase()
                .onSuccess { user ->
                    preferenceRepository.updateCurrentUser(user)
                }
        }
        emit(preferenceRepository.currentUser.value)
    }.flowOn(Dispatchers.IO)
}