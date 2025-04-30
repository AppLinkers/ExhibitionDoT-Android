package com.exhibitiondot.presentation.ui.screen.main.updateUserInfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.exhibitiondot.domain.model.Category
import com.exhibitiondot.domain.model.EventType
import com.exhibitiondot.domain.model.Region
import com.exhibitiondot.domain.model.UpdateUserInfo
import com.exhibitiondot.domain.usecase.user.GetCacheFirstUserFlowUseCase
import com.exhibitiondot.domain.usecase.user.UpdateUserInfoUseCase
import com.exhibitiondot.presentation.base.BaseViewModel
import com.exhibitiondot.presentation.mapper.getMessage
import com.exhibitiondot.presentation.model.GlobalUiModel
import com.exhibitiondot.presentation.ui.state.EditTextState
import com.exhibitiondot.presentation.ui.state.MultiFilterState
import com.exhibitiondot.presentation.ui.state.SingleFilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserInfoViewModel @Inject constructor(
    private val getCacheFirstUserFlowUseCase: GetCacheFirstUserFlowUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val uiModel: GlobalUiModel,
) : BaseViewModel() {
    var uiState by mutableStateOf<UpdateUserInfoUiState>(UpdateUserInfoUiState.PostLoading)
        private set

    val nicknameState = EditTextState(maxLength = 10)
    val regionState = SingleFilterState(filterList = Region.entries)
    val categoryState = MultiFilterState(filterList = Category.entries)
    val eventTypeState = MultiFilterState(filterList = EventType.entries)

    init {
        viewModelScope.launch {
            val user = getCacheFirstUserFlowUseCase().first()
            with(user) {
                nicknameState.typeText(nickname)
                regionState.setFilter(region)
                categoryState.setFilter(categoryList)
                eventTypeState.setFilter(eventTypeList)
            }.also {
                uiState = UpdateUserInfoUiState.Idle
            }
        }
    }

    fun validate(): Boolean {
        return nicknameState.isValidate() &&
                regionState.selectedFilter != null
    }

    fun onUpdate(onBack: () -> Unit) {
        uiState = UpdateUserInfoUiState.Loading
        viewModelScope.launch {
            val updateUserInfo = UpdateUserInfo(
                nickname = nicknameState.trimmedText(),
                region = regionState.selectedFilter!!,
                categoryList = categoryState.selectedFilterList,
                eventTypeList = eventTypeState.selectedFilterList
            )
            updateUserInfoUseCase(updateUserInfo)
                .onSuccess {
                    uiModel.showToast("정보를 수정했어요")
                    onBack()
                }
                .onFailure { t ->
                    val msg = t.getMessage("정보 수정에 실패했어요")
                    uiModel.showToast(msg)
                    uiState = UpdateUserInfoUiState.Idle
                }
        }
    }
}