package com.exhibitiondot.data.datasource.user

import com.exhibitiondot.data.model.dto.UserDto
import com.exhibitiondot.data.model.request.ChangeUserInfoRequest
import com.exhibitiondot.data.model.request.SignInRequest
import com.exhibitiondot.data.model.request.SignUpRequest
import com.exhibitiondot.data.model.response.SignInResponse
import com.exhibitiondot.data.network.NetworkState

interface UserDataSource {
    suspend fun sigIn(signInRequest: SignInRequest): NetworkState<SignInResponse>

    suspend fun signUp(signUpRequest: SignUpRequest): NetworkState<Unit>

    suspend fun getUser(): NetworkState<UserDto>

    suspend fun changeUserInfo(changeUserInfoRequest: ChangeUserInfoRequest): NetworkState<Unit>
}