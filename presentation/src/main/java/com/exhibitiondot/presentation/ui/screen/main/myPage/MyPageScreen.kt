package com.exhibitiondot.presentation.ui.screen.main.myPage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.model.UserUiModel
import com.exhibitiondot.presentation.ui.component.DoTLoadingScreen
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DoTTopBar
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun MyPageRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MyPageScreen(
        modifier = modifier,
        uiState = uiState,
        onBack = onBack
    )
    BackHandler(onBack = onBack)
}

@Composable
private fun MyPageScreen(
    modifier: Modifier,
    uiState: MyPageUiState,
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DoTTopBar(
            title = stringResource(R.string.my_page),
            onBack = onBack
        )
        when (uiState) {
            MyPageUiState.Loading -> DoTLoadingScreen(
                modifier = Modifier.weight(1f)
            )
            is MyPageUiState.Success -> MyPageBody(
                modifier = Modifier.weight(1f),
                user = uiState.user
            )
        }

    }
}

@Composable
private fun MyPageBody(
    modifier: Modifier = Modifier,
    user: UserUiModel,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(all = screenPadding)
    ) {
        MyPageTitle(title = stringResource(R.string.my_page_user_info))
        MyPageAccount(user = user)
        DoTSpacer(size = 40)
        MyPageTitle(title = stringResource(R.string.my_page_category))
        DoTSpacer(size = 20)
        MyPageTags(tags = user.categoryTags)
        DoTSpacer(size = 40)
        MyPageTitle(title = stringResource(R.string.my_page_event_type))
        DoTSpacer(size = 20)
        MyPageTags(tags = user.eventTypeTags)
    }
}

@Composable
private fun MyPageTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    )
}

@Composable
private fun MyPageAccount(user: UserUiModel) {
    MyPageUserInfo(
        label = stringResource(R.string.email),
        value = user.email
    )
    MyPageUserInfo(
        label = stringResource(R.string.name),
        value = user.name
    )
    MyPageUserInfo(
        label = stringResource(R.string.nickname),
        value = user.nickname
    )
    MyPageUserInfo(
        label = stringResource(R.string.phone),
        value = user.phone
    )
    MyPageUserInfo(
        label = stringResource(R.string.region),
        value = user.region
    )
}

@Composable
private fun MyPageUserInfo(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.surfaceContainerLow
        )
        Text(
            text = value,
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Composable
private fun MyPageTags(tags: String) {
    Text(
        text = tags,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary
    )
}