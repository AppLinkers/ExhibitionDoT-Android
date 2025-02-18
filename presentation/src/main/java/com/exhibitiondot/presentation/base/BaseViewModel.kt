package com.exhibitiondot.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun useFlag(state: MutableStateFlow<Boolean>, onFlag: () -> Unit) {
        viewModelScope.launch {
            state.collect { stateTrue ->
                if (stateTrue) {
                    onFlag()
                    state.update { false }
                }
            }
        }
    }
}