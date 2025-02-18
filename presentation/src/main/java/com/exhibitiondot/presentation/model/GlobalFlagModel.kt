package com.exhibitiondot.presentation.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalFlagModel @Inject constructor() {
    private val _homeUpdateFlag = MutableStateFlow(false)
    val homeUpdateFlag: StateFlow<Boolean> = _homeUpdateFlag.asStateFlow()

    fun setHomeUpdateFlag(needUpdate: Boolean) {
        _homeUpdateFlag.update { needUpdate }
    }
}