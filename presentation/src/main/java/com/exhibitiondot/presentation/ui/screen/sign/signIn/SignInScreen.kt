package com.exhibitiondot.presentation.ui.screen.sign.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.EmailLoginButton
import com.exhibitiondot.presentation.ui.component.KakaoLoginButton
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun SignInRoute(
    modifier: Modifier = Modifier,
    moveSignUp: (String) -> Unit,
    moveEmailSignIn: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SignInScreen(
        modifier = modifier,
        uiState = uiState,
        onLogin = { viewModel.onKakaoLogin(context, moveSignUp) },
        onEmailLogin = moveEmailSignIn,
    )
}

@Composable
private fun SignInScreen(
    modifier: Modifier,
    uiState: SignInUiState,
    onLogin: () -> Unit,
    onEmailLogin: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(
                vertical = 40.dp,
                horizontal = screenPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DoTSpacer(size = 180)
        Image(
            modifier = Modifier.size(140.dp),
            painter = painterResource(R.drawable.app_logo),
            contentDescription = "app-logo"
        )
        DoTSpacer(size = 24)
        Text(
            text = stringResource(R.string.app_catch_phrase),
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.labelLarge
        )
        DoTSpacer(modifier = Modifier.weight(1f))
        KakaoLoginButton(
            enabled = uiState != SignInUiState.Loading,
            onLogin = onLogin
        )

        DoTSpacer(size = 10)
        EmailLoginButton(onLogin = onEmailLogin)
    }
}