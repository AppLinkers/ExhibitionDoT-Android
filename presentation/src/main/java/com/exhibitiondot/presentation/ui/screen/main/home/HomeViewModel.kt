package com.exhibitiondot.presentation.ui.screen.main.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventParams
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.domain.usecase.event.GetEventListUseCase
import com.exhibitiondot.domain.usecase.user.GetCacheFirstUserFlowUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.mapper.toUiModel
import com.exhibitiondot.presentation.model.EventUiModel
import com.exhibitiondot.presentation.ui.state.EditTextState
import com.exhibitiondot.presentation.ui.state.MultiFilterState
import com.exhibitiondot.presentation.ui.state.SingleFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCacheFirstUserFlowUseCase: GetCacheFirstUserFlowUseCase,
    private val getEventListUseCase: GetEventListUseCase
) : BaseViewModel() {
    private val _eventParams = MutableStateFlow(EventParams.NONE)
    val eventParams: StateFlow<EventParams> = _eventParams.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val eventList: Flow<PagingData<EventUiModel>> =
        eventParams
            .flatMapLatest { params ->
                getEventListUseCase(params)
            }
            .map { pagingData ->
                pagingData.map(Event::toUiModel)
            }
            .cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Nothing)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val regionState = SingleFilterState(
        filterList = Region.values()
    )
    val categoryState = MultiFilterState(
        filterList = Category.values()
    )
    val eventTypeState = MultiFilterState(
        filterList = EventType.values()
    )
    val queryState = EditTextState(maxLength = 20)

    init {
        viewModelScope.launch {
            getCacheFirstUserFlowUseCase().collect { user ->
                _eventParams.update {
                    eventParams.value.copy(
                        region = user.region,
                        categoryList = user.categoryList,
                        eventTypeList = user.eventTypeList,
                    )
                }
                regionState.setFilter(user.region)
                categoryState.setFilter(user.categoryList)
                eventTypeState.setFilter(user.eventTypeList)
            }
        }
    }

    fun resetAllFilters() {
        _eventParams.update { EventParams.NONE }
        regionState.resetAll()
        categoryState.resetAll()
        eventTypeState.resetAll()
    }

    fun resetAppliedQuery() {
        queryState.resetText()
        _eventParams.update { eventParams.value.copy(query = "") }
    }

    fun applyQuery() {
        _eventParams.update { eventParams.value.copy(query = queryState.trimmedText()) }
        dismiss()
    }

    fun updateUiState(uiState: HomeUiState) {
        _uiState.update { uiState }
    }

    fun dismiss() {
        updateUiState(HomeUiState.Nothing)
    }

    fun applyRegionFilter() {
        _eventParams.update { eventParams.value.copy(region = regionState.selectedFilter) }
    }

    fun applyCategoryFilter() {
        _eventParams.update { eventParams.value.copy(categoryList = categoryState.selectedFilterList) }
    }

    fun applyEventTypeFilter() {
        _eventParams.update { eventParams.value.copy(eventTypeList = eventTypeState.selectedFilterList) }
    }
}