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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.component.DoTButton
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DoTTopBar
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun SignUpRoute(
    modifier: Modifier = Modifier,
    moveMain: () -> Unit,
    onBack: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val step by viewModel.currentStep.collectAsStateWithLifecycle()
    val selectedRegion by viewModel.selectedRegion.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedEventType by viewModel.selectedEventType.collectAsStateWithLifecycle()
    val progress by animateFloatAsState(step.percentage, label = "progress-anim")

    SignUpScreen(
        modifier = modifier,
        uiState = uiState,
        step = step,
        progress = progress,
        selectedRegion = selectedRegion,
        selectedCategory = selectedCategory,
        selectedEventType = selectedEventType,
        onPrevStep = { viewModel.onPrevStep(step, onBack) },
        onNextStep = { viewModel.onNextStep(step, moveMain, onBack) },
        selectRegion = viewModel::selectRegion,
        selectCategory = viewModel::selectCategory,
        selectEventType = viewModel::selectEventType
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
    selectedRegion: Region,
    selectedCategory: List<Category>,
    selectedEventType: List<EventType>,
    onPrevStep: () -> Unit,
    onNextStep: () -> Unit,
    selectRegion: (Region) -> Unit,
    selectCategory: (Category) -> Unit,
    selectEventType: (EventType) -> Unit,
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
                .padding(all = screenPadding)
        ) {
            Text(
                text = stringResource(step.description),
                style = MaterialTheme.typography.displayLarge
            )
            when (step) {
                SignUpStep.InfoStep -> {}
                SignUpStep.RegionStep -> {}
                SignUpStep.CategoryStep -> {}
                SignUpStep.EventTypeStep -> {}
            }
            DoTSpacer(modifier = Modifier.weight(1f))
            DoTButton(
                text = if (step.onNextStep() != null) {
                    stringResource(R.string.next)
                } else {
                    stringResource(R.string.signup)
                },
                onClick = onNextStep
            )
        }
    }
}