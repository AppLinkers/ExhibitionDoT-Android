package com.exhibitiondot.presentation.ui.screen.main.myPage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.model.UserUiModel
import com.exhibitiondot.presentation.ui.component.DoTAlertDialog
import com.exhibitiondot.presentation.ui.component.DoTLoadingScreen
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DoTTitle
import com.exhibitiondot.presentation.ui.component.DoTTopBar
import com.exhibitiondot.presentation.ui.component.EditIcon
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun MyPageRoute(
    modifier: Modifier = Modifier,
    moveUpdateUserInfo: () -> Unit,
    onBack: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    MyPageScreen(
        modifier = modifier,
        uiState = uiState,
        moveUpdateUserInfo = moveUpdateUserInfo,
        onSignOut = viewModel::signOut,
        onWithDraw = { showDialog = true },
        onPrivacyPolicy = {},
        onBack = onBack
    )
    DoTAlertDialog(
        show = showDialog,
        title = stringResource(R.string.withdraw),
        text = stringResource(R.string.withdraw_description),
        onConfirm = viewModel::signOut,
        onDismiss = { showDialog = false }
    )
}

@Composable
private fun MyPageScreen(
    modifier: Modifier,
    uiState: MyPageUiState,
    moveUpdateUserInfo: () -> Unit,
    onSignOut: () -> Unit,
    onWithDraw: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DoTTopBar(
            title = stringResource(R.string.my_page),
            onBack = onBack,
            trailingIcon = {
                EditIcon(onClick = moveUpdateUserInfo)
            }
        )
        when (uiState) {
            MyPageUiState.Loading -> DoTLoadingScreen(
                modifier = Modifier.weight(1f)
            )
            is MyPageUiState.Success -> MyPageBody(
                modifier = Modifier.weight(1f),
                user = uiState.user,
                onSignOut = onSignOut,
                onWithDraw = onWithDraw,
                onPrivacyPolicy = onPrivacyPolicy,
            )
        }

    }
}

@Composable
private fun MyPageBody(
    modifier: Modifier = Modifier,
    user: UserUiModel,
    onSignOut: () -> Unit,
    onWithDraw: () -> Unit,
    onPrivacyPolicy: () -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(
                start = screenPadding,
                end = screenPadding,
                top = 10.dp,
                bottom = screenPadding
            )
    ) {
        DoTTitle(title = stringResource(R.string.my_page_user_info))
        DoTSpacer(size = 10)
        MyPageAccount(user = user)
        DoTSpacer(size = 40)

        DoTTitle(title = stringResource(R.string.my_page_category))
        DoTSpacer(size = 20)
        MyPageTags(tags = user.categoryTags)
        DoTSpacer(size = 60)

        DoTTitle(title = stringResource(R.string.my_page_event_type))
        DoTSpacer(size = 20)
        MyPageTags(tags = user.eventTypeTags)
        DoTSpacer(size = 60)

        DoTTitle(title = stringResource(R.string.my_page_manage_account))
        DoTSpacer(size = 10)
        MyPageTextBox(
            text = stringResource(R.string.logout),
            onClick = onSignOut
        )
        MyPageTextBox(
            text = stringResource(R.string.withdraw),
            onClick = onWithDraw
        )
        DoTSpacer(size = 60)

        DoTTitle(title = stringResource(R.string.my_page_terms_and_policy))
        DoTSpacer(size = 10)
        MyPageTextBox(
            text = stringResource(R.string.my_page_privacy_policy),
            onClick = onPrivacyPolicy
        )
        DoTSpacer(size = 60)
    }
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

@Composable
private fun MyPageTextBox(
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium
        )
    }
}