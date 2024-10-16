package com.exhibitiondot.data.datasource

import com.exhibitiondot.data.api.UserApi
import com.exhibitiondot.data.model.request.ChangeUserInfoRequest
import com.exhibitiondot.data.model.request.SignInRequest
import com.exhibitiondot.data.model.request.SignUpRequest
import com.exhibitiondot.data.model.response.SignInResponse
import com.exhibitiondot.data.network.NetworkState

class UserRemoteDataSource(
    private val userApi: UserApi
) : UserDataSource {
    override suspend fun sigIn(signInRequest: SignInRequest): NetworkState<SignInResponse> {
        return userApi.signIn(signInRequest)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): NetworkState<Void> {
        return userApi.signUp(signUpRequest)
    }

    override suspend fun changeUserInfo(changeUserInfoRequest: ChangeUserInfoRequest): NetworkState<Void> {
        return userApi.changeUserInfo(changeUserInfoRequest)
    }
}