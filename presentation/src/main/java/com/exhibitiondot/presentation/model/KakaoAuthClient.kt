package com.exhibitiondot.presentation.model

import android.content.Context
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import javax.inject.Inject

class KakaoAuthClient @Inject constructor() {
    fun loginWithKakao(
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    loginWithKakaoAccount(context, onSuccess, onFailure)
                } else if (token != null) {
                    onSuccess()
                }
            }
        } else {
            loginWithKakaoAccount(context, onSuccess, onFailure)
        }
    }

    private fun loginWithKakaoAccount(
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                onFailure(error)
            } else if (token != null) {
                onSuccess()
            }
        }
    }

    fun getUserEmail(
        onSuccess: (String) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                onFailure(error)
            } else if (user != null) {
                user.kakaoAccount?.let { account ->
                    account.email?.run(onSuccess)
                }
            }
        }
    }

    fun logOut() {
        UserApiClient.instance.logout {  }
    }
}