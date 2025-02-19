package com.exhibitiondot.presentation.ui.screen.main.eventDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.exhibitiondot.domain.model.Comment
import com.exhibitiondot.domain.usecase.comment.AddCommentUseCase
import com.exhibitiondot.domain.usecase.comment.GetCommentListUseCase
import com.exhibitiondot.domain.usecase.event.DeleteEventUseCase
import com.exhibitiondot.domain.usecase.event.GetEventDetailUseCase
import com.exhibitiondot.domain.usecase.event.ToggleEventLikeUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.mapper.getMessage
import com.exhibitiondot.presentation.mapper.toUiModel
import com.exhibitiondot.presentation.model.CommentUiModel
import com.exhibitiondot.presentation.model.EventDetailUiModel
import com.exhibitiondot.presentation.model.GlobalFlagModel
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.ui.navigation.MainScreen
import com.exhibitiondot.presentation.ui.state.EditTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val toggleEventLikeUseCase: ToggleEventLikeUseCase,
    private val getEventDetailUseCase: GetEventDetailUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    getCommentListUseCase: GetCommentListUseCase,
    private val uiModel: GlobalUiModel,
    private val flagModel: GlobalFlagModel,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    private val eventId = savedStateHandle.toRoute<MainScreen.EventDetail>().eventId

    var uiState by mutableStateOf<EventDetailUiState>(EventDetailUiState.Loading)
        private set

    var dialogState by mutableStateOf<EventDetailDialogState>(EventDetailDialogState.Nothing)
        private set

    val commentList: Flow<PagingData<CommentUiModel>> =
        getCommentListUseCase(eventId)
            .map { pagingData -> pagingData.map(Comment::toUiModel) }
            .cachedIn(viewModelScope)

    val commentState = EditTextState(maxLength = 400)

    init {
        getEventDetail()
    }

    private fun getEventDetail() {
        viewModelScope.launch {
            getEventDetailUseCase(eventId)
                .onSuccess { eventDetail ->
                    uiState = EventDetailUiState.Success(eventDetail.toUiModel())
                }
                .onFailure {
                    uiState = EventDetailUiState.Failure
                }
        }
    }

    fun toggleEventLike(eventDetail: EventDetailUiModel) {
        viewModelScope.launch {
            toggleEventLikeUseCase(eventId)
                .onSuccess {
                    val newEventDetail = if (eventDetail.isLike) {
                        eventDetail.copy(
                            isLike = false,
                            likeCount = eventDetail.likeCount - 1
                        )
                    } else {
                        eventDetail.copy(
                            isLike = true,
                            likeCount = eventDetail.likeCount + 1
                        )
                    }
                    uiState = EventDetailUiState.Success(newEventDetail)
                }
                .onFailure { t ->
                    val msg = t.getMessage("좋아요 변경에 실패했어요")
                    uiModel.showToast(msg)
                }
        }
    }

    fun showDialog(eventDetailDialogState: EventDetailDialogState) {
        dialogState = eventDetailDialogState
    }

    fun dismiss() {
        dialogState = EventDetailDialogState.Nothing
    }

    fun onUpdate(movePostEvent: (Long?) -> Unit) {
        movePostEvent(eventId)
    }

    fun onDelete(onBack: () -> Unit) {
        viewModelScope.launch {
            deleteEventUseCase(eventId)
                .onSuccess {
                    flagModel.setHomeUpdateFlag(true)
                    uiModel.showToast("이벤트를 삭제했어요")
                    onBack()
                }
                .onFailure { t ->
                    val msg = t.getMessage("이벤트 삭제에 실패했어요")
                    uiModel.showToast(msg)
                }
        }
    }

    fun onReport() {
        uiModel.showToast("게시글을 신고했어요")
    }

    fun addComment(refresh: () -> Unit) {
        viewModelScope.launch {
            addCommentUseCase(eventId, commentState.trimmedText())
                .onSuccess {
                    commentState.resetText()
                    refresh()
                }
                .onFailure { t ->
                    val msg = t.getMessage("댓글 작성에 실패했어요")
                    uiModel.showToast(msg)
                }
        }
    }
}