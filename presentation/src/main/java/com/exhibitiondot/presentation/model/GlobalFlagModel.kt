package com.exhibitiondot.presentation.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalFlagModel @Inject constructor() {
    val homeUpdateFlag = MutableStateFlow(false)

    fun updateHome() {
        homeUpdateFlag.update { true }
    }
}