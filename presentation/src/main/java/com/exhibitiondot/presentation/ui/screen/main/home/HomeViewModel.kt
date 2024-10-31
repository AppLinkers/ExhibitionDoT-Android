package com.exhibitiondot.presentation.ui.screen.main.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.Event
import com.exhibitiondot.domain.model.EventParams
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Filter
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.domain.usecase.event.GetEventListUseCase
import com.exhibitiondot.domain.usecase.user.GetCachedUserUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.mapper.toUiModel
import com.exhibitiondot.presentation.model.EventUiModel
import com.exhibitiondot.presentation.ui.state.EditTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCachedUserUseCase: GetCachedUserUseCase,
    private val getEventListUseCase: GetEventListUseCase
) : BaseViewModel() {
    private val _appliedRegion = MutableStateFlow<Region?>(null)
    val appliedRegion: StateFlow<Region?> = _appliedRegion.asStateFlow()

    private val _appliedCategory = MutableStateFlow<List<Category>>(emptyList())
    val appliedCategory: StateFlow<List<Category>> = _appliedCategory.asStateFlow()

    private val _appliedEventType = MutableStateFlow<List<EventType>>(emptyList())
    val appliedEventType: StateFlow<List<EventType>> = _appliedEventType.asStateFlow()

    private val _appliedQuery = MutableStateFlow("")
    val appliedQuery: StateFlow<String> = _appliedQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val eventList: Flow<PagingData<EventUiModel>> =
        combine(
            flow = _appliedRegion,
            flow2 = _appliedCategory,
            flow3 = _appliedEventType,
            flow4 = _appliedQuery
        ) { region, categoryList, eventTypeList, query ->
            EventParams(region, categoryList, eventTypeList, query)
        }.flatMapLatest { params ->
            getEventListUseCase(params)
        }.map { pagingData ->
            pagingData.map(Event::toUiModel)
        }.cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Nothing)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _selectedRegion = MutableStateFlow<Region?>(null)
    val selectedRegion: StateFlow<Region?> = _selectedRegion.asStateFlow()

    private val _selectedCategory = MutableStateFlow<List<Category>>(emptyList())
    val selectedCategory: StateFlow<List<Category>> = _selectedCategory.asStateFlow()

    private val _selectedEventType = MutableStateFlow<List<EventType>>(emptyList())
    val selectedEventType: StateFlow<List<EventType>> = _selectedEventType.asStateFlow()

    val queryState = EditTextState(maxLength = 20)

    private val regionList = Region.values()
    private val categoryList = Category.values()
    private val eventTypeList = EventType.values()

    fun initializeFilterStates() {
        getCachedUserUseCase().value.run {
            _appliedRegion.update { region }
            _selectedRegion.update { region }
            _appliedCategory.update { categoryList }
            _selectedCategory.update { categoryList }
            _appliedEventType.update { eventTypeList }
            _selectedEventType.update { eventTypeList }
        }
    }

    fun canResetFilters(): Boolean {
        return _appliedRegion.value != null ||
                _appliedCategory.value.isNotEmpty() ||
                _appliedEventType.value.isNotEmpty() ||
                _appliedQuery.value.isNotEmpty()
    }

    fun resetAllFilters() {
        _appliedRegion.update { null }
        _selectedRegion.update { null }
        _appliedCategory.update { emptyList() }
        _selectedCategory.update { emptyList() }
        _appliedEventType.update { emptyList() }
        _selectedEventType.update { emptyList() }
        resetAppliedQuery()
    }

    fun resetAppliedQuery() {
        _appliedQuery.update { "" }
        queryState.resetText()
    }

    fun showFilterSheet(filterState: HomeUiState.FilterState) {
        _uiState.update { filterState }
    }

    fun getFilterList(filterState: HomeUiState.FilterState): List<Filter> {
        return when (filterState) {
            HomeUiState.FilterState.ShowRegionFilter -> regionList
            HomeUiState.FilterState.ShowCategoryFilter -> categoryList
            HomeUiState.FilterState.ShowEventTypeFilter -> eventTypeList
        }
    }

    fun selectAll(filterState: HomeUiState.FilterState) {
        when (filterState) {
            HomeUiState.FilterState.ShowRegionFilter -> _selectedRegion.update { null }
            HomeUiState.FilterState.ShowCategoryFilter -> _selectedCategory.update { emptyList() }
            HomeUiState.FilterState.ShowEventTypeFilter -> _selectedEventType.update { emptyList() }
        }
    }

    fun selectFilter(filterState: HomeUiState.FilterState, filter: Filter) {
        when (filterState) {
            HomeUiState.FilterState.ShowRegionFilter -> selectRegion(filter as Region)
            HomeUiState.FilterState.ShowCategoryFilter -> selectCategory(filter as Category)
            HomeUiState.FilterState.ShowEventTypeFilter -> selectEventType(filter as EventType)
        }
    }

    private fun selectRegion(region: Region) {
        _selectedRegion.update { region }
    }

    private fun selectCategory(category: Category) {
        if (category in selectedCategory.value) {
            _selectedCategory.update {
                selectedCategory.value.filter { it != category }
            }
        } else {
            if (_selectedCategory.value.size + 1 == categoryList.size) {
                selectAll(HomeUiState.FilterState.ShowCategoryFilter)
            } else {
                _selectedCategory.update { selectedCategory.value + category }
            }
        }
    }

    private fun selectEventType(eventType: EventType) {
        if (eventType in selectedEventType.value) {
            _selectedEventType.update {
                selectedEventType.value.filter { it != eventType }
            }
        } else {
            if (_selectedEventType.value.size + 1 == eventTypeList.size) {
                selectAll(HomeUiState.FilterState.ShowEventTypeFilter)
            } else {
                _selectedEventType.update { selectedEventType.value + eventType }
            }
        }
    }

    fun applyFilters(filterState: HomeUiState.FilterState) {
        when (filterState) {
            HomeUiState.FilterState.ShowRegionFilter -> _appliedRegion.update { selectedRegion.value }
            HomeUiState.FilterState.ShowCategoryFilter -> _appliedCategory.update { selectedCategory.value }
            HomeUiState.FilterState.ShowEventTypeFilter -> _appliedEventType.update { selectedEventType.value }
        }
    }

    fun showSearchDialog() {
        _uiState.update { HomeUiState.ShowSearchDialog }
    }

    fun applyQuery() {
        _appliedQuery.update { queryState.trimmedText() }
        dismiss()
    }

    fun dismiss() {
        _uiState.update { HomeUiState.Nothing }
    }
}