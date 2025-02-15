package com.exhibitiondot.presentation.ui.screen.main.eventDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.exhibitiondot.domain.model.Comment
import com.exhibitiondot.domain.usecase.comment.GetCommentListUseCase
import com.exhibitiondot.domain.usecase.event.GetEventDetailUseCase
import com.exhibitiondot.domain.usecase.event.ToggleEventLikeUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.mapper.toUiModel
import com.exhibitiondot.presentation.model.CommentUiModel
import com.exhibitiondot.presentation.model.EventDetailUiModel
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.ui.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val toggleEventLikeUseCase: ToggleEventLikeUseCase,
    private val getEventDetailUseCase: GetEventDetailUseCase,
    getCommentListUseCase: GetCommentListUseCase,
    private val uiModel: GlobalUiModel,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    private val eventId = savedStateHandle.toRoute<MainScreen.EventDetail>().eventId

    private val _uiState = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Loading)
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

    val commentList: Flow<PagingData<CommentUiModel>> =
        getCommentListUseCase(eventId)
            .map { pagingData -> pagingData.map(Comment::toUiModel) }
            .cachedIn(viewModelScope)

    init {
        getEventDetail()
    }

    private fun getEventDetail() {
        viewModelScope.launch {
            getEventDetailUseCase(eventId)
                .onSuccess { eventDetail ->
                    _uiState.update { EventDetailUiState.Success(eventDetail.toUiModel()) }
                }
                .onFailure {
                    _uiState.update { EventDetailUiState.Failure }
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
                    _uiState.update { EventDetailUiState.Success(newEventDetail) }
                }
                .onFailure {
                    uiModel.showToast("좋아요 변경에 실패했어요")
                }
        }
    }
}