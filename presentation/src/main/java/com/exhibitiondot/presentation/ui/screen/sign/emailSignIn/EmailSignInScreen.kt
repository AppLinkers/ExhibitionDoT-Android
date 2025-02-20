package com.exhibitiondot.presentation.ui.screen.sign.emailSignIn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.component.DoTButton
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DoTTextField
import com.exhibitiondot.presentation.ui.component.DoTTopBar
import com.exhibitiondot.presentation.ui.state.IEditTextState
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun EmailSignInRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: EmailSignInViewModel = hiltViewModel(),
) {
    val buttonEnabled by remember { derivedStateOf { viewModel.emailState.isValidate() } }
    EmailSignInScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        emailState = viewModel.emailState,
        buttonEnabled = buttonEnabled,
        onSignIn = viewModel::signIn,
        onBack = onBack,
    )
}

@Composable
fun EmailSignInScreen(
    modifier: Modifier,
    uiState: EmailSignInUiState,
    emailState: IEditTextState,
    buttonEnabled: Boolean,
    onSignIn: () -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        DoTTopBar(
            title = stringResource(R.string.email_login),
            onBack = onBack
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = screenPadding,
                    end = screenPadding,
                    top = screenPadding,
                    bottom = 40.dp
                )
        ) {
            DoTTextField(
                value = emailState.typedText,
                placeHolder = stringResource(R.string.email_sign_in_placeholder),
                onValueChange = emailState::typeText,
                onResetValue = emailState::resetText
            )
            DoTSpacer(modifier = Modifier.weight(1f))
            DoTButton(
                text = stringResource(R.string.login),
                isLoading = uiState == EmailSignInUiState.Loading,
                enabled = buttonEnabled,
                onClick = onSignIn
            )
        }
    }
}