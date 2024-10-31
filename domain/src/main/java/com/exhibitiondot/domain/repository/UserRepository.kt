package com.exhibitiondot.domain.repository

import com.exhibitiondot.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun cacheUser(): Result<Unit>

    fun getUser(): StateFlow<User>

    suspend fun signIn(email: String): Result<Unit>

    suspend fun signUp(user: User): Result<Unit>

    suspend fun changeUserInfo(user: User): Result<Unit>
}