package com.exhibitiondot.presentation.ui.screen.main.postEvent

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.ImageSource
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.ui.component.DoTButton
import com.exhibitiondot.presentation.ui.component.DoTTopBar
import com.exhibitiondot.presentation.ui.component.PostEventImage
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
        when (currentStep) {
            PostEventStep.UploadImage -> UploadImageScreen(
                modifier= Modifier.weight(1f),
                image = image,
                addImage = addImage,
                deleteImage = deleteImage
            )
            PostEventStep.EventInfo -> EventInfoScreen(
                modifier = Modifier.weight(1f),
                nameState = nameState,
                regionState = regionState,
                categoryState = categoryState,
                eventTypeState = eventTypeState,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = screenPadding,
                    end = screenPadding,
                    bottom = 40.dp
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
    addImage: () -> Unit,
    deleteImage: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = screenPadding)
            .verticalScroll(rememberScrollState())
    ) {
        PostEventTitle(title = stringResource(R.string.event_post_image))
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
    regionState: ISingleFilterState<Region>,
    categoryState: IMultiFilerState<Category>,
    eventTypeState: IMultiFilerState<EventType>,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {

    }
}

@Composable
private fun PostEventTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    )
}