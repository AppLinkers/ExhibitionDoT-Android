package com.exhibitiondot.presentation.ui.screen.main.myPage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exhibitiondot.presentation.model.UserUiModel
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun MyPageRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsStateWithLifecycle()
    MyPageScreen(
        modifier = modifier,
        user = user,
        onBack = onBack
    )
    BackHandler(onBack = onBack)
}

@Composable
private fun MyPageScreen(
    modifier: Modifier,
    user: UserUiModel,
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(all = screenPadding)
        ) {

        }
    }
}