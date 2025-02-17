package com.exhibitiondot.presentation.ui.screen.main.postEvent

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.ImageSource
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.mapper.DateFormatStrategy
import com.exhibitiondot.presentation.mapper.format
import com.exhibitiondot.presentation.ui.component.CalendarIcon
import com.exhibitiondot.presentation.ui.component.DoTButton
import com.exhibitiondot.presentation.ui.component.DoTDatePickerDialog
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DoTTextField
import com.exhibitiondot.presentation.ui.component.DoTTitle
import com.exhibitiondot.presentation.ui.component.DoTTopBar
import com.exhibitiondot.presentation.ui.component.MultiFilterSelectScreen
import com.exhibitiondot.presentation.ui.component.PostEventImage
import com.exhibitiondot.presentation.ui.component.SingleFilterSelectScreen
import com.exhibitiondot.presentation.ui.state.IEditTextState
import com.exhibitiondot.presentation.ui.state.IMultiFilerState
import com.exhibitiondot.presentation.ui.state.ISingleFilterState
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun PostEventRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: PostEventViewModel = hiltViewModel(),
) {
    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.onPhotoPickerResult(uri) }
    )
    val buttonEnabled by remember { derivedStateOf { viewModel.validate() } }

    PostEventScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        currentStep = viewModel.currentStep,
        image = viewModel.image,
        nameState = viewModel.nameState,
        date = viewModel.selectedDate,
        regionState = viewModel.regionState,
        categoryState = viewModel.categoryState,
        eventTypeState = viewModel.eventTypeState,
        editMode = viewModel.editMode,
        buttonEnabled = buttonEnabled,
        lastStep = viewModel.lastStep(),
        onPrevStep = { viewModel.onPrevStep(onBack) },
        onNextStep = { viewModel.onNextStep(onBack) },
        addImage = {
            pickMedia.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        deleteImage = viewModel::deleteImage,
        showPhotoPicker = viewModel::showDatePicker
    )
    DoTDatePickerDialog(
        show = viewModel.uiState == PostEventUiState.ShowDatePicker,
        date = viewModel.selectedDate,
        onDateSelect = viewModel::onDateSelect,
        onDismiss = viewModel::dismiss
    )
    BackHandler {
        viewModel.onPrevStep(onBack)
    }
}

@Composable
private fun PostEventScreen(
    modifier: Modifier,
    uiState: PostEventUiState,
    currentStep: PostEventStep,
    image: ImageSource?,
    nameState: IEditTextState,
    date: String,
    regionState: ISingleFilterState<Region>,
    categoryState: IMultiFilerState<Category>,
    eventTypeState: IMultiFilerState<EventType>,
    editMode: Boolean,
    buttonEnabled: Boolean,
    lastStep: Boolean,
    onPrevStep: () -> Unit,
    onNextStep: () -> Unit,
    addImage: () -> Unit,
    deleteImage: () -> Unit,
    showPhotoPicker: () -> Unit
) {
    Column (
        modifier = modifier.fillMaxSize()
    ) {
       DoTTopBar(
           title = if (editMode) {
               stringResource(R.string.event_post_update)
           } else {
               stringResource(R.string.event_post_add)
           },
           onBack = onPrevStep
       )
        val uploadImageScrollState = rememberScrollState()
        val eventInfoScrollState = rememberScrollState()
        when (currentStep) {
            PostEventStep.UploadImage -> UploadImageScreen(
                modifier= Modifier.weight(1f),
                image = image,
                scrollState = uploadImageScrollState,
                addImage = addImage,
                deleteImage = deleteImage
            )
            PostEventStep.EventInfo -> EventInfoScreen(
                modifier = Modifier.weight(1f),
                nameState = nameState,
                date = date,
                regionState = regionState,
                categoryState = categoryState,
                eventTypeState = eventTypeState,
                scrollState = eventInfoScrollState,
                showPhotoPicker = showPhotoPicker
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = screenPadding,
                    end = screenPadding,
                    top = 10.dp,
                    bottom = 30.dp
                )
        ) {
            DoTButton(
                text = if (lastStep.not()) {
                    stringResource(R.string.next)
                } else if (editMode) {
                    stringResource(R.string.update)
                } else {
                    stringResource(R.string.add)
                },
                isLoading = uiState == PostEventUiState.Loading,
                enabled = buttonEnabled,
                onClick = onNextStep
            )
        }
    }
}



@Composable
private fun UploadImageScreen(
    modifier: Modifier = Modifier,
    image: ImageSource?,
    scrollState: ScrollState,
    addImage: () -> Unit,
    deleteImage: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding)
            .verticalScroll(scrollState)
    ) {
        DoTTitle(
            title = stringResource(R.string.event_post_image)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            PostEventImage(
                image = image,
                addImage = addImage,
                deleteImage = deleteImage
            )
        }
    }
}

@Composable
private fun EventInfoScreen(
    modifier: Modifier = Modifier,
    nameState: IEditTextState,
    date: String,
    regionState: ISingleFilterState<Region>,
    categoryState: IMultiFilerState<Category>,
    eventTypeState: IMultiFilerState<EventType>,
    scrollState: ScrollState,
    showPhotoPicker: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding)
            .verticalScroll(scrollState)
    ) {
        DoTTitle(
            title = stringResource(R.string.event_post_name)
        )
        DoTSpacer(size = 5)
        DoTTextField(
            value = nameState.typedText,
            placeHolder = stringResource(R.string.event_post_name),
            onValueChange = nameState::typeText,
            onResetValue = nameState::resetText,
            imeAction = ImeAction.Done
        )
        DoTSpacer(size = 40)
        DoTTitle(
            title = stringResource(R.string.event_post_select_date)
        )
        DoTSpacer(size = 15)
        EventInfoDateView(
            date = date,
            onClick = showPhotoPicker
        )
        DoTSpacer(size = 40)
        DoTTitle(
            title = stringResource(R.string.event_post_select_region)
        )
        DoTSpacer(size = 15)
        SingleFilterSelectScreen(filterState = regionState)
        DoTSpacer(size = 40)
        DoTTitle(
            title = stringResource(R.string.event_post_select_category)
        )
        DoTSpacer(size = 15)
        MultiFilterSelectScreen(filterState = categoryState)
        DoTSpacer(size = 40)
        DoTTitle(
            title = stringResource(R.string.event_post_select_event_type)
        )
        DoTSpacer(size = 15)
        MultiFilterSelectScreen(filterState = eventTypeState)
        DoTSpacer(size = 40)
    }
}

@Composable
private fun EventInfoDateView(
    modifier: Modifier = Modifier,
    date: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = format(DateFormatStrategy.FullDate(date)),
            style = MaterialTheme.typography.labelLarge
        )
        CalendarIcon()
    }
}