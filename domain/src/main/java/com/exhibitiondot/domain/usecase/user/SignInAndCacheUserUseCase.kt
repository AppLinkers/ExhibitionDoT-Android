package com.exhibitiondot.domain.usecase.user

import javax.inject.Inject

class SignInAndCacheUserUseCase @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val cacheUserUseCase: CacheUserUseCase
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return if (signInUseCase(email).isSuccess && cacheUserUseCase().isSuccess) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalStateException())
        }
    }
}