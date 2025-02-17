package com.exhibitiondot.presentation.ui.screen.sign.signUp

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.component.DoTButton
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DoTTextField
import com.exhibitiondot.presentation.ui.component.DoTTopBar
import com.exhibitiondot.presentation.ui.component.MultiFilterSelectScreen
import com.exhibitiondot.presentation.ui.component.SingleFilterSelectScreen
import com.exhibitiondot.presentation.ui.state.IEditTextState
import com.exhibitiondot.presentation.ui.state.IMultiFilerState
import com.exhibitiondot.presentation.ui.state.ISingleFilterState
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun SignUpRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val step by viewModel.currentStep.collectAsStateWithLifecycle()
    val progress by animateFloatAsState(step.percentage, label = "progress-anim")
    val buttonEnabled by remember { derivedStateOf { viewModel.validate() } }

    SignUpScreen(
        modifier = modifier,
        uiState = uiState,
        step = step,
        progress = progress,
        buttonEnabled = buttonEnabled,
        nameState = viewModel.nameState,
        nicknameState = viewModel.nicknameState,
        phoneState = viewModel.phoneState,
        regionState = viewModel.regionState,
        categoryState = viewModel.categoryState,
        eventTypeState = viewModel.eventTypeState,
        onPrevStep = { viewModel.onPrevStep(step, onBack) },
        onNextStep = { viewModel.onNextStep(step, onBack) },
    )
    BackHandler {
        viewModel.onPrevStep(step, onBack)
    }
}

@Composable
private fun SignUpScreen(
    modifier: Modifier,
    uiState: SignUpUiState,
    step: SignUpStep,
    progress: Float,
    buttonEnabled: Boolean,
    nameState: IEditTextState,
    nicknameState: IEditTextState,
    phoneState: IEditTextState,
    regionState: ISingleFilterState<Region>,
    categoryState: IMultiFilerState<Category>,
    eventTypeState: IMultiFilerState<EventType>,
    onPrevStep: () -> Unit,
    onNextStep: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DoTTopBar(
            title = stringResource(R.string.signup),
            onBack = onPrevStep
        )
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = { progress }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = screenPadding,
                    vertical = 40.dp
                )
        ) {
            Text(
                text = stringResource(step.description),
                style = MaterialTheme.typography.displayLarge
            )
            DoTSpacer(size = 30)
            when (step) {
                SignUpStep.InfoStep -> InfoStepScreen(
                    nameState = nameState,
                    nicknameState = nicknameState,
                    phoneState = phoneState
                )
                SignUpStep.RegionStep -> SingleFilterSelectScreen(filterState = regionState)
                SignUpStep.CategoryStep -> MultiFilterSelectScreen(filterState = categoryState)
                SignUpStep.EventTypeStep -> MultiFilterSelectScreen(filterState = eventTypeState)
            }
            DoTSpacer(modifier = Modifier.weight(1f))
            DoTButton(
                text = if (step.nextStep() != null) {
                    stringResource(R.string.next)
                } else {
                    stringResource(R.string.signup)
                },
                isLoading = uiState == SignUpUiState.Loading,
                enabled = buttonEnabled,
                onClick = onNextStep
            )
        }
    }
}

@Composable
fun InfoStepScreen(
    modifier: Modifier = Modifier,
    nameState: IEditTextState,
    nicknameState: IEditTextState,
    phoneState: IEditTextState,
) {
    Column(modifier) {
        DoTSpacer(size = 20)
        DoTTextField(
            value = nameState.typedText,
            placeHolder = stringResource(R.string.signup_name_placeholder),
            onValueChange = nameState::typeText,
            onResetValue = nameState::resetText,
            imeAction = ImeAction.Next
        )
        DoTSpacer(size = 30)
        DoTTextField(
            value = nicknameState.typedText,
            placeHolder = stringResource(R.string.signup_nickname_placeholder),
            onValueChange = nicknameState::typeText,
            onResetValue = nicknameState::resetText,
            imeAction = ImeAction.Next

        )
        DoTSpacer(size = 30)
        DoTTextField(
            value = phoneState.typedText,
            placeHolder = stringResource(R.string.signup_phone_placeholder),
            onValueChange = phoneState::typeText,
            onResetValue = phoneState::resetText,
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        )
    }
}

