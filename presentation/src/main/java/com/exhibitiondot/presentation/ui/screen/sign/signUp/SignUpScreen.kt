package com.exhibitiondot.presentation.ui.screen.sign.signUp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
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

    SignUpScreen(
        modifier = modifier,
        uiState = uiState,
        step = step,
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
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(all = screenPadding)
        ) {
            when (step) {
                SignUpStep.InfoStep -> {}
                SignUpStep.RegionStep -> TODO()
                SignUpStep.CategoryStep -> TODO()
                SignUpStep.EventTypeStep -> TODO()
            }
        }
    }
}