package com.exhibitiondot.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.exhibitiondot.domain.model.UpdateUserInfo
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceRepository {
    private val _currentUser = MutableStateFlow(User.NONE)
    override val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    override val userId: Flow<String?> = dataStore.data.map { store -> store[KEY_USER_ID] }

    override fun updateCurrentUser(user: User) {
        _currentUser.update { user }
    }

    override fun updateCurrentUser(updateUserInfo: UpdateUserInfo) {
        _currentUser.update {
            with(updateUserInfo) {
                currentUser.value.copy(
                    nickname =  nickname,
                    region = region,
                    categoryList = categoryList,
                    eventTypeList = eventTypeList
                )
            }
        }
    }

    override suspend fun updateUserId(userId: Long) {
        dataStore.edit { store ->
            store[KEY_USER_ID] = userId.toString()
        }
    }

    companion object {
        private val KEY_USER_ID = stringPreferencesKey("user-id")
    }
}