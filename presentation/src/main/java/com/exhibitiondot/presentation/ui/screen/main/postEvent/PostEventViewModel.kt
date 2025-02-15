package com.exhibitiondot.presentation.ui.screen.main.postEvent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventInfo
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.ImageSource
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.domain.usecase.event.AddEventUseCase
import com.exhibitiondot.domain.usecase.event.GetEventInfoUseCase
import com.exhibitiondot.domain.usecase.event.UpdateEventUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.ui.navigation.MainScreen
import com.exhibitiondot.presentation.ui.state.EditTextState
import com.exhibitiondot.presentation.ui.state.MultiFilterState
import com.exhibitiondot.presentation.ui.state.SingleFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostEventViewModel @Inject constructor(
    private val addEventUseCase: AddEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    private val getEventInfoUseCase: GetEventInfoUseCase,
    private val uiModel: GlobalUiModel,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    private val eventId = savedStateHandle.toRoute<MainScreen.PostEvent>().eventId
    private lateinit var originEventInfo: EventInfo
    val editMode = eventId != null

    var uiState by mutableStateOf<PostEventUiState>(PostEventUiState.Idle)
        private set
    var currentStep by mutableStateOf(PostEventStep.UploadImage)
        private set
    private val totalSteps = PostEventStep.entries

    var image by mutableStateOf<ImageSource?>(null)
        private set

    val nameState = EditTextState(maxLength = 30)
    val regionState = SingleFilterState(filterList = Region.values())
    val categoryState = MultiFilterState(filterList = Category.values())
    val eventTypeState = MultiFilterState(filterList = EventType.values())

    init {
        eventId?.let {
            setEventInfo(it)
        }
    }

    private fun setEventInfo(eventId: Long) {
        viewModelScope.launch {
            getEventInfoUseCase(eventId)
                .onSuccess { (eventInfo, imageSource) ->
                    with(eventInfo) {
                        originEventInfo = this
                        nameState.typeText(name)
                        regionState.setFilter(region)
                        categoryState.setFilter(categoryList)
                        eventTypeState.setFilter(eventTypeList)
                    }
                    image = imageSource
                }
        }
    }

    fun onPrevStep(onBack: () -> Unit) {
        val prevIdx = totalSteps.indexOf(currentStep) - 1
        if (prevIdx < 0) {
            onBack()
        } else {
            currentStep = totalSteps[prevIdx]
        }
    }

    fun onNextStep(onBack: () -> Unit) {
        val nextIdx = totalSteps.indexOf(currentStep) + 1
        if (nextIdx > totalSteps.lastIndex) {
            postingEvent(onBack)
        } else {
            currentStep = totalSteps[nextIdx]
        }
    }

    fun validate(): Boolean {
        return when (currentStep) {
            PostEventStep.UploadImage -> image != null
            PostEventStep.EventInfo -> nameState.isValidate() && regionState.selectedFilter != null // TODO("조건 추가")
        }
    }

    private fun postingEvent(onBack: () -> Unit) {
        uiState = PostEventUiState.Loading
        viewModelScope.launch {
            val eventInfo = EventInfo(
                name = nameState.trimmedText(),
                date = "", // TODO("입력 날짜 할당")
                region = regionState.selectedFilter!!,
                categoryList = categoryState.selectedFilterList,
                eventTypeList = eventTypeState.selectedFilterList,
            )
            if (editMode) {
                updateEvent(eventId!!, eventInfo, onBack)
            } else {
                addEvent(eventInfo, onBack)
            }
        }
    }

    private suspend fun addEvent(eventInfo: EventInfo, onBack: () -> Unit) {
        addEventUseCase(image as ImageSource.Local, eventInfo)
            .onSuccess {
                showMessage("이벤트를 추가했어요")
                onBack()
            }
            .onFailure {
                showMessage("이벤트 추가에 실패했어요")
            }
    }

    private suspend fun updateEvent(
        eventId: Long,
        eventInfo: EventInfo,
        onBack: () -> Unit
    ) {
        updateEventUseCase(image!!, originEventInfo, eventInfo, eventId)
            .onSuccess {
                showMessage("이벤트를 수정했어요")
                onBack()
            }
            .onFailure {
                showMessage("이벤트 수정에 실패했어요")
            }
    }

    private fun showMessage(msg: String) {
        uiState = PostEventUiState.Idle
        uiModel.showToast(msg)
    }
}