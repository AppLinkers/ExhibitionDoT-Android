package com.exhibitiondot.data.repository

import com.exhibitiondot.data.datasource.user.UserDataSource
import com.exhibitiondot.data.mapper.toChangeUserInfoRequest
import com.exhibitiondot.data.mapper.toDomain
import com.exhibitiondot.data.mapper.toSignUpRequest
import com.exhibitiondot.data.model.request.SignInRequest
import com.exhibitiondot.data.network.NetworkState
import com.exhibitiondot.domain.exception.NetworkFailException
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUser(): Result<User> {
        val response = userDataSource.getUser()
        return when (response) {
            is NetworkState.Success -> Result.success(response.data.toDomain())
            is NetworkState.Failure -> Result.failure(
                NetworkFailException(response.code, response.error)
            )
            else -> Result.failure(IllegalStateException())
        }
    }

    override suspend fun signIn(email: String): Result<Long> {
        val response = userDataSource.sigIn(SignInRequest(email))
        return when (response) {
            is NetworkState.Success -> Result.success(response.data.userId)
            is NetworkState.Failure -> Result.failure(
                NetworkFailException(response.code, response.error)
            )
            else -> Result.failure(IllegalStateException())
        }
    }

    override suspend fun signUp(user: User): Result<Unit> {
        val response = userDataSource.signUp(user.toSignUpRequest())
        return when (response) {
            is NetworkState.Success -> Result.success(response.data)
            is NetworkState.Failure -> Result.failure(
                NetworkFailException(response.code, response.error)
            )
            else -> Result.failure(IllegalStateException())
        }
    }

    override suspend fun changeUserInfo(user: User): Result<Unit> {
        val response = userDataSource.changeUserInfo(user.toChangeUserInfoRequest())
        return when (response) {
            is NetworkState.Success -> Result.success(response.data)
            is NetworkState.Failure -> Result.failure(
                NetworkFailException(response.code, response.error)
            )
            else -> Result.failure(IllegalStateException())
        }
    }
}