package com.exhibitiondot.data.datasource.user

import com.exhibitiondot.data.network.api.UserApi
import com.exhibitiondot.data.network.model.dto.UserDto
import com.exhibitiondot.data.network.model.request.UpdateUserInfoRequest
import com.exhibitiondot.data.network.model.request.SignInRequest
import com.exhibitiondot.data.network.model.request.SignUpRequest
import com.exhibitiondot.data.network.model.response.SignInResponse
import com.exhibitiondot.data.network.NetworkState
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi
) : UserDataSource {
    override suspend fun sigIn(signInRequest: SignInRequest): NetworkState<SignInResponse> {
        return userApi.signIn(signInRequest)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): NetworkState<Unit> {
        return userApi.signUp(signUpRequest)
    }

    override suspend fun getUser(): NetworkState<UserDto> {
        return userApi.getUser()
    }

    override suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest): NetworkState<Unit> {
        return userApi.updateUserInfo(updateUserInfoRequest)
    }
}