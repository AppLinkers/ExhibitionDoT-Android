package com.exhibitiondot.presentation.ui.screen.main.eventDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.exhibitiondot.domain.model.Comment
import com.exhibitiondot.domain.model.EventDetail
import com.exhibitiondot.domain.usecase.comment.GetCommentListUseCase
import com.exhibitiondot.domain.usecase.event.GetEventDetailUseCase
import com.exhibitiondot.domain.usecase.event.ToggleEventLikeUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.mapper.toUiModel
import com.exhibitiondot.presentation.model.CommentUiModel
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.ui.navigation.KEY_EVENT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val toggleEventLikeUseCase: ToggleEventLikeUseCase,
    getEventDetailUseCase: GetEventDetailUseCase,
    getCommentListUseCase: GetCommentListUseCase,
    private val uiModel: GlobalUiModel,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    private val eventId: Long = checkNotNull(savedStateHandle[KEY_EVENT_ID])

    val uiState: StateFlow<EventDetailUiState> =
        getEventDetailUseCase(eventId)
            .map(EventDetail::toUiModel)
            .map(EventDetailUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = EventDetailUiState.Loading
            )

    val commentList: Flow<PagingData<CommentUiModel>> =
        getCommentListUseCase(eventId)
            .map { pagingData -> pagingData.map(Comment::toUiModel) }
            .cachedIn(viewModelScope)

    fun toggleEventLike(isLike: Boolean) {
        viewModelScope.launch {
            toggleEventLikeUseCase(eventId)
                .onFailure {
                    uiModel.showToast("좋아요 변경에 실패했어요")
                }
        }
    }
}