package com.exhibitiondot.presentation.ui.screen.sign.signUp

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Filter
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.component.DoTButton
import com.exhibitiondot.presentation.ui.component.DoTFilterChip
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DoTTextField
import com.exhibitiondot.presentation.ui.component.DoTTopBar
import com.exhibitiondot.presentation.ui.state.IEditTextState
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
        validate = viewModel.validate(step),
        nameState = viewModel.nameState,
        nicknameState = viewModel.nicknameState,
        phoneState = viewModel.phoneState,
        regionList = viewModel.regionList,
        selectedRegion = selectedRegion,
        categoryList = viewModel.categoryList,
        selectedCategory = selectedCategory,
        eventTypeList = viewModel.eventTypeList,
        selectedEventType = selectedEventType,
        onPrevStep = { viewModel.onPrevStep(step, onBack) },
        onNextStep = { viewModel.onNextStep(step, moveMain, onBack) },
        selectFilter = viewModel::selectFilter
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
    validate: Boolean,
    nameState: IEditTextState,
    nicknameState: IEditTextState,
    phoneState: IEditTextState,
    regionList: List<Region>,
    selectedRegion: Region,
    categoryList: List<Category>,
    selectedCategory: List<Category>,
    eventTypeList: List<EventType>,
    selectedEventType: List<EventType>,
    onPrevStep: () -> Unit,
    onNextStep: () -> Unit,
    selectFilter: (Filter) -> Unit,
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
            when (step) {
                SignUpStep.InfoStep -> InfoStepScreen(
                    nameState = nameState,
                    nicknameState = nicknameState,
                    phoneState = phoneState
                )
                SignUpStep.RegionStep -> FilterSelectScreen(
                    filterList = regionList,
                    selectedFilter = selectedRegion,
                    onSelectFilter = selectFilter
                )
                SignUpStep.CategoryStep -> FilterSelectScreen(
                    filterList = categoryList,
                    selectedFilterList = selectedCategory,
                    onSelectFilter = selectFilter
                )
                SignUpStep.EventTypeStep -> FilterSelectScreen(
                    filterList = eventTypeList,
                    selectedFilterList = selectedEventType,
                    onSelectFilter = selectFilter
                )
            }
            DoTSpacer(modifier = Modifier.weight(1f))
            DoTButton(
                text = if (step.nextStep() != null) {
                    stringResource(R.string.next)
                } else {
                    stringResource(R.string.signup)
                },
                isLoading = uiState == SignUpUiState.Loading,
                enabled = validate,
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
            imeAction = ImeAction.Done
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSelectScreen(
    modifier: Modifier = Modifier,
    filterList: List<Filter>,
    selectedFilter: Filter? = null,
    selectedFilterList: List<Filter> = emptyList(),
    onSelectFilter: (Filter) -> Unit
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        filterList.forEach { filter ->
            DoTFilterChip(
                name = when (filter) {
                    is Region -> filter.name
                    is Category -> filter.key
                    is EventType -> filter.key
                    else -> ""
                },
                selected = when (filter) {
                    is Filter.SingleFilter -> filter == selectedFilter
                    is Filter.MultiFilter -> filter in selectedFilterList
                },
                onSelect = { onSelectFilter(filter) }
            )
        }
    }
}
