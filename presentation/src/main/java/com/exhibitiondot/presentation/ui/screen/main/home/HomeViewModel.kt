package com.exhibitiondot.presentation.ui.screen.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.exhibitiondot.presentation.model.GlobalFlagModel
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
    private val getEventListUseCase: GetEventListUseCase,
    val flagModel: GlobalFlagModel,
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

    var uiState by mutableStateOf<HomeUiState>(HomeUiState.Idle)
        private set

    val regionState = SingleFilterState(filterList = Region.values())
    val categoryState = MultiFilterState(filterList = Category.values())
    val eventTypeState = MultiFilterState(filterList = EventType.values())

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
        _eventParams.update { eventParams.value.copy(query = "") }
    }

    fun applyQuery(query: String) {
        _eventParams.update { eventParams.value.copy(query = query) }
    }

    fun showSearchDialog() {
        uiState = HomeUiState.ShowSearchDialog(eventParams.value.query)
    }

    fun showRegionFilter() {
        regionState.setFilter(eventParams.value.region)
        uiState = HomeUiState.ShowRegionFilter
    }

    fun showCategoryFilter() {
        categoryState.setFilter(eventParams.value.categoryList)
        uiState = HomeUiState.ShowCategoryFilter
    }

    fun showEventTypeFilter() {
        eventTypeState.setFilter(eventParams.value.eventTypeList)
        uiState = HomeUiState.ShowEventTypeFilter
    }

    fun dismiss() {
        uiState = HomeUiState.Idle
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