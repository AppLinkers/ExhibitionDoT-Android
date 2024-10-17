package com.exhibitiondot.data.datasource

import com.exhibitiondot.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthDataSource {
    val currentUser: StateFlow<User>

    val userId: Flow<Long>

    val email: Flow<String>

    fun updateCurrentUser(user: User)

    suspend fun updateAuthInfo(userId: Long, email: String)
}