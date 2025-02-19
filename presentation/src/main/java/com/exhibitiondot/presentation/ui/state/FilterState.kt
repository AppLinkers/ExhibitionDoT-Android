package com.exhibitiondot.presentation.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.exhibitiondot.domain.model.Filter

interface IFilterState<T : Filter> {
    val filterList: List<T>
    fun selectFilter(filter: T)
    fun resetAll()
}

interface ISingleFilterState<T : Filter.SingleFilter> : IFilterState<T> {
    var selectedFilter: T?
    fun setFilter(filter: T?)
}

interface IMultiFilerState<T : Filter.MultiFilter> : IFilterState<T> {
    var selectedFilterList: List<T>
    fun setFilter(filterList: List<T>)
}

class SingleFilterState<T : Filter.SingleFilter>(
    initFilter: T? = null,
    override val filterList: List<T>,
) : ISingleFilterState<T> {
    override var selectedFilter by mutableStateOf(initFilter)

    override fun selectFilter(filter: T) {
        selectedFilter = filter
    }

    override fun setFilter(filter: T?) {
        selectedFilter = filter
    }

    override fun resetAll() {
        selectedFilter = null
    }
}

open class MultiFilterState<T : Filter.MultiFilter>(
    initFilterList: List<T> = emptyList(),
    override val filterList: List<T>,
) : IMultiFilerState<T> {
    override var selectedFilterList by mutableStateOf(initFilterList)

    override fun selectFilter(filter: T) {
        selectedFilterList = if (filter in selectedFilterList) {
            selectedFilterList - filter
        } else {
            selectedFilterList + filter
        }
    }

    override fun setFilter(filterList: List<T>) {
        selectedFilterList = filterList
    }

    override fun resetAll() {
        selectedFilterList = emptyList()
    }
}

class MultiFilterForQueryState<T : Filter.MultiFilter>(
    initFilterList: List<T> = emptyList(),
    override val filterList: List<T>,
) : MultiFilterState<T>(initFilterList, filterList) {

    override fun selectFilter(filter: T) {
        if (selectedFilterList.size + 1 == filterList.size) {
            selectedFilterList = emptyList()
        } else {
            super.selectFilter(filter)
        }
    }

    override fun setFilter(filterList: List<T>) {
        if (this.filterList.size == filterList.size) {
            selectedFilterList = emptyList()
        } else {
            super.setFilter(filterList)
        }
    }
}