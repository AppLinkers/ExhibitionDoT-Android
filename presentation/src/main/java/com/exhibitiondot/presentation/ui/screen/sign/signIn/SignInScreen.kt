package com.exhibitiondot.presentation.ui.screen.sign.signIn

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignInRoute(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    moveMain: () -> Unit,
    moveSignUp: (String) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SignInScreen(
        modifier = modifier,
        uiState = uiState,
        onLogin = { viewModel.onKakaoLogin(context, moveMain, moveSignUp) }
    )
}

@Composable
private fun SignInScreen(
    modifier: Modifier,
    uiState: SignInUiState,
    onLogin: () -> Unit
) {

}