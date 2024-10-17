package com.exhibitiondot.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.exhibitiondot.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class AuthLocalDataSource @Inject constructor(
    private val authStore: DataStore<Preferences>
) : AuthDataSource {
    private val _currentUser = MutableStateFlow(User.NONE)
    override val currentUser: StateFlow<User> = _currentUser

    override val userId: Flow<String?> = authStore.data.map { store -> store[KEY_USER_ID] }

    override val email: Flow<String?> = authStore.data.map { store -> store[KEY_USER_EMAIL] }

    override fun updateCurrentUser(user: User) {
        _currentUser.update { user }
    }

    override suspend fun updateAuthInfo(userId: Long, email: String) {
        authStore.edit { store ->
            store[KEY_USER_ID] = userId.toString()
            store[KEY_USER_EMAIL] = email
        }
    }

    companion object {
        private val KEY_USER_ID = stringPreferencesKey("user-id")
        private val KEY_USER_EMAIL = stringPreferencesKey("user-email")
    }
}