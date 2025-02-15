package com.exhibitiondot.domain.repository

import com.exhibitiondot.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PreferenceRepository {
    val currentUser: StateFlow<User>

    val userId: Flow<String?>

    fun updateCurrentUser(user: User)

    suspend fun updateUserId(userId: Long)
}