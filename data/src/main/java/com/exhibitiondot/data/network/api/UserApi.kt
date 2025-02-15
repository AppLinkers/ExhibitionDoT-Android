package com.exhibitiondot.data.network.api

import com.exhibitiondot.data.network.model.dto.UserDto
import com.exhibitiondot.data.network.model.request.ChangeUserInfoRequest
import com.exhibitiondot.data.network.model.request.SignInRequest
import com.exhibitiondot.data.network.model.request.SignUpRequest
import com.exhibitiondot.data.network.model.response.SignInResponse
import com.exhibitiondot.data.network.NetworkState
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserApi {
    @POST("user/login")
    suspend fun signIn(@Body signInRequest: SignInRequest): NetworkState<SignInResponse>

    @POST("user")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): NetworkState<Unit>

    @GET("user")
    suspend fun getUser(): NetworkState<UserDto>

    @PATCH("user")
    suspend fun changeUserInfo(@Body changeUserInfoRequest: ChangeUserInfoRequest): NetworkState<Unit>
}