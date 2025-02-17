package com.exhibitiondot.presentation.ui.screen.main.updateUserInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.component.DoTButton
import com.exhibitiondot.presentation.ui.component.DoTLoadingScreen
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DoTTextField
import com.exhibitiondot.presentation.ui.component.DoTTitle
import com.exhibitiondot.presentation.ui.component.DoTTopBar
import com.exhibitiondot.presentation.ui.component.MultiFilterSelectScreen
import com.exhibitiondot.presentation.ui.component.SingleFilterSelectScreen
import com.exhibitiondot.presentation.ui.state.IEditTextState
import com.exhibitiondot.presentation.ui.state.IMultiFilerState
import com.exhibitiondot.presentation.ui.state.ISingleFilterState
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun UpdateUserInfoRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: UpdateUserInfoViewModel = hiltViewModel(),
) {
    UpdateUserInfoScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        nicknameState = viewModel.nicknameState,
        regionState = viewModel.regionState,
        categoryState = viewModel.categoryState,
        eventTypeState = viewModel.eventTypeState,
        validate = viewModel::validate,
        onUpdate = { viewModel.onUpdate(onBack) },
        onBack = onBack,
    )
}

@Composable
private fun UpdateUserInfoScreen(
    modifier: Modifier,
    uiState: UpdateUserInfoUiState,
    nicknameState: IEditTextState,
    regionState: ISingleFilterState<Region>,
    categoryState: IMultiFilerState<Category>,
    eventTypeState: IMultiFilerState<EventType>,
    validate: () -> Boolean,
    onUpdate: () -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DoTTopBar(
            title = stringResource(R.string.update_user_info),
            onBack = onBack
        )
        if (uiState == UpdateUserInfoUiState.PostLoading) {
            DoTLoadingScreen(modifier = Modifier.weight(1f))
        } else {
            UpdateUserInfoBody(
                modifier = Modifier.weight(1f),
                uiState = uiState,
                nicknameState = nicknameState,
                regionState = regionState,
                categoryState = categoryState,
                eventTypeState = eventTypeState,
                validate = validate,
                onUpdate = onUpdate,
            )
        }
    }
}

@Composable
private fun UpdateUserInfoBody(
    modifier: Modifier = Modifier,
    uiState: UpdateUserInfoUiState,
    nicknameState: IEditTextState,
    regionState: ISingleFilterState<Region>,
    categoryState: IMultiFilerState<Category>,
    eventTypeState: IMultiFilerState<EventType>,
    validate: () -> Boolean,
    onUpdate: () -> Unit,
) {
    val buttonEnabled by remember { derivedStateOf { validate() } }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(
                start = screenPadding,
                end = screenPadding,
                top = 10.dp,
                bottom = screenPadding,
            )
    ) {
        DoTTitle(
            title = stringResource(R.string.nickname)
        )
        DoTSpacer(size = 5)
        DoTTextField(
            value = nicknameState.typedText,
            placeHolder = stringResource(R.string.nickname),
            onValueChange = nicknameState::typeText,
            onResetValue = nicknameState::resetText,
            imeAction = ImeAction.Done
        )
        DoTSpacer(size = 40)
        DoTTitle(
            title = stringResource(R.string.signup_region_step_description)
        )
        DoTSpacer(size = 20)
        SingleFilterSelectScreen(filterState = regionState)
        DoTSpacer(size = 40)
        DoTTitle(
            title = stringResource(R.string.signup_category_step_description)
        )
        DoTSpacer(size = 20)
        MultiFilterSelectScreen(filterState = categoryState)
        DoTSpacer(size = 40)
        DoTTitle(
            title = stringResource(R.string.signup_event_type_step_description)
        )
        DoTSpacer(size = 20)
        MultiFilterSelectScreen(filterState = eventTypeState)
        DoTSpacer(size = 100)
        DoTButton(
            text = stringResource(R.string.update),
            enabled = buttonEnabled,
            isLoading = uiState == UpdateUserInfoUiState.Loading,
            onClick = onUpdate
        )
    }
}