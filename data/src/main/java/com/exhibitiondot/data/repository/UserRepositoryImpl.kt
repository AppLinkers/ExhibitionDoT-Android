package com.exhibitiondot.data.repository

import com.exhibitiondot.data.datasource.UserDataSource
import com.exhibitiondot.data.mapper.toChangeUserInfoRequest
import com.exhibitiondot.data.mapper.toSignUpRequest
import com.exhibitiondot.data.model.request.SignInRequest
import com.exhibitiondot.data.network.NetworkState
import com.exhibitiondot.domain.exception.NetworkFailException
import com.exhibitiondot.domain.model.User
import com.exhibitiondot.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {
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
            is NetworkState.Success -> Result.success(Unit)
            is NetworkState.Failure -> Result.failure(
                NetworkFailException(response.code, response.error)
            )
            else -> Result.failure(IllegalStateException())
        }
    }

    override suspend fun changeUserInfo(user: User): Result<Unit> {
        val response = userDataSource.changeUserInfo(user.toChangeUserInfoRequest())
        return when (response) {
            is NetworkState.Success -> Result.success(Unit)
            is NetworkState.Failure -> Result.failure(
                NetworkFailException(response.code, response.error)
            )
            else -> Result.failure(IllegalStateException())
        }
    }
}