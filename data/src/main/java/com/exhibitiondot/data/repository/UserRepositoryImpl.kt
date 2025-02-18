package com.exhibitiondot.data.repository

import com.exhibitiondot.data.datasource.user.UserDataSource
import com.exhibitiondot.data.mapper.toDomain
import com.exhibitiondot.data.mapper.toRequest
import com.exhibitiondot.data.mapper.toResult
import com.exhibitiondot.data.mapper.toSignUpRequest
import com.exhibitiondot.data.network.model.request.SignInRequest
import com.exhibitiondot.data.network.model.dto.UserDto
import com.exhibitiondot.domain.model.UpdateUserInfo
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUser(): Result<User> {
        return userDataSource.getUser().toResult(UserDto::toDomain)
    }

    override suspend fun signIn(email: String): Result<Long> {
        val request = SignInRequest(email)
        return userDataSource.sigIn(request).toResult { it.userId }
    }

    override suspend fun signUp(user: User): Result<Unit> {
        val request = user.toSignUpRequest()
        return userDataSource.signUp(request).toResult { }
    }

    override suspend fun updateUserInfo(updateUserInfo: UpdateUserInfo): Result<Unit> {
        val request = updateUserInfo.toRequest()
        return userDataSource.updateUserInfo(request).toResult { }
    }
}