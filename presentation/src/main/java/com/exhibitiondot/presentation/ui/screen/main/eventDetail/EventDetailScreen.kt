package com.exhibitiondot.presentation.ui.screen.main.eventDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.exhibitiondot.presentation.R
import com.exhibitiondot.presentation.model.CommentUiModel
import com.exhibitiondot.presentation.model.EventDetailUiModel
import com.exhibitiondot.presentation.ui.component.CommentTextField
import com.exhibitiondot.presentation.ui.component.DoTImage
import com.exhibitiondot.presentation.ui.component.DoTLoadingScreen
import com.exhibitiondot.presentation.ui.component.DoTAlertDialog
import com.exhibitiondot.presentation.ui.component.DoTEmptyScreen
import com.exhibitiondot.presentation.ui.component.DoTRefreshScreen
import com.exhibitiondot.presentation.ui.component.DoTSpacer
import com.exhibitiondot.presentation.ui.component.DoTUpdateDeleteDialog
import com.exhibitiondot.presentation.ui.component.EventDetailTopBar
import com.exhibitiondot.presentation.ui.component.HeartIcon
import com.exhibitiondot.presentation.ui.component.MenuIcon
import com.exhibitiondot.presentation.ui.component.SendIcon
import com.exhibitiondot.presentation.ui.state.IEditTextState
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun EventDetailRoute(
    modifier: Modifier = Modifier,
    movePostEvent: (Long?) -> Unit,
    onBack: () -> Unit,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val dialogState = viewModel.dialogState
    val commentList = viewModel.commentList.collectAsLazyPagingItems()

    when (uiState) {
        EventDetailUiState.Loading -> DoTLoadingScreen(modifier = modifier)
        EventDetailUiState.Failure -> {}
        is EventDetailUiState.Success -> EventDetailScreen(
            modifier = modifier,
            eventDetail = uiState.eventDetail,
            commentList = commentList,
            commentState = viewModel.commentState,
            toggleEventLike = viewModel::toggleEventLike,
            showDialog = viewModel::showDialog,
            addComment = { viewModel.addComment { commentList.refresh() } },
            onBack = onBack
        )
    }
    DoTAlertDialog(
        show = dialogState == EventDetailDialogState.ShowReportDialog,
        title = stringResource(R.string.report),
        text = stringResource(R.string.report_description),
        onConfirm = viewModel::onReport,
        onDismiss = viewModel::dismiss
    )
    DoTUpdateDeleteDialog(
        show = dialogState == EventDetailDialogState.ShowUpdateDeleteDialog,
        onUpdate = { viewModel.onUpdate(movePostEvent) },
        onDelete = { viewModel.onDelete(onBack) },
        onDismiss = viewModel::dismiss
    )
}

@Composable
private fun EventDetailScreen(
    modifier: Modifier,
    eventDetail: EventDetailUiModel,
    commentList: LazyPagingItems<CommentUiModel>,
    commentState: IEditTextState,
    toggleEventLike: (EventDetailUiModel) -> Unit,
    showDialog: (EventDetailDialogState) -> Unit,
    addComment: () -> Unit,
    onBack: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val skipImage by remember {
        derivedStateOf {
            (lazyListState.firstVisibleItemIndex == 0 &&
                    lazyListState.firstVisibleItemScrollOffset < 1100).not()
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = PaddingValues(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                EventDetailView(
                    eventDetail = eventDetail,
                    toggleEventLike = toggleEventLike,
                )
            }
            when (commentList.loadState.refresh) {
                LoadState.Loading -> item {
                    DoTLoadingScreen(
                        modifier = Modifier.height(200.dp)
                    )
                }
                is LoadState.Error -> item {
                    DoTRefreshScreen(
                        modifier = Modifier.height(200.dp),
                        onRefresh = commentList::refresh
                    )
                }
                is LoadState.NotLoading -> {
                    if (commentList.itemCount == 0) {
                        item {
                            DoTEmptyScreen(
                                modifier = Modifier.height(200.dp),
                                description = stringResource(R.string.comment_empty_description)
                            )
                        }
                    } else {
                        items(
                            count = commentList.itemCount,
                            key = commentList.itemKey { it.id }
                        ) { index ->
                            commentList[index]?.let { comment ->
                                CommentItem(
                                    comment = comment,
                                    showDialog = { showDialog(EventDetailDialogState.ShowReportDialog) }
                                )
                            }
                        }
                    }
                }
            }
        }
        EventDetailTopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            eventName = eventDetail.name,
            skipImage = skipImage,
            showDialog = {
                if (eventDetail.owner) {
                    showDialog(EventDetailDialogState.ShowUpdateDeleteDialog)
                } else {
                    showDialog(EventDetailDialogState.ShowReportDialog)
                }
            },
            onBack = onBack
        )
        EventDetailCommentView(
            modifier = Modifier.align(Alignment.BottomCenter),
            commentState = commentState,
            addComment = addComment
        )
    }
}

@Composable
private fun EventDetailView(
    modifier: Modifier = Modifier,
    eventDetail: EventDetailUiModel,
    toggleEventLike: (EventDetailUiModel) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 3 / 4f),
        ) {
            DoTImage(
                modifier = Modifier.fillMaxSize(),
                url = eventDetail.imgUrl,
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.2f),
                                Color.Black.copy(alpha = 0.1f),
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                            )
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = screenPadding)
        ) {
            DoTSpacer(size = 30)
            Text(
                text = eventDetail.date,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            DoTSpacer(size = 6)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = eventDetail.name,
                style = MaterialTheme.typography.titleLarge,
                lineHeight = 28.sp
            )
            DoTSpacer(size = 8)
            Text(
                text = "${eventDetail.region} · ${eventDetail.createdAt}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.surfaceContainer,
                fontSize = 14.sp
            )
            DoTSpacer(size = 70)
            Text(
                text = eventDetail.eventTypeTags,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                fontSize = 14.sp
            )
            DoTSpacer(size = 6)
            Text(
                text = eventDetail.categoryTags,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                fontSize = 14.sp
            )
            DoTSpacer(size = 20)
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeartIcon(
                    modifier = Modifier.clickable(
                        onClick = { toggleEventLike(eventDetail) }
                    ),
                    size = 28,
                    isLike = eventDetail.isLike
                )
                DoTSpacer(size = 4)
                Text(
                    text = "${eventDetail.likeCount}",
                    style = MaterialTheme.typography.displayMedium,
                )
            }
            DoTSpacer(size = 60)
            Text(
                text = stringResource(R.string.comment),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            )
        }
    }
}

@Composable
private fun CommentItem(
    modifier: Modifier = Modifier,
    comment: CommentUiModel,
    showDialog: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = screenPadding)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${comment.nickname} ㆍ ${comment.createdAt}",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            )
            DoTSpacer(size = 14)
            Text(
                text = comment.content,
                style = MaterialTheme.typography.displayMedium,
                lineHeight = 24.sp
            )
        }
        MenuIcon(
            modifier = Modifier.size(16.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            onClick = showDialog
        )
    }
}

@Composable
private fun EventDetailCommentView(
    modifier: Modifier = Modifier,
    commentState: IEditTextState,
    addComment: () -> Unit,
) {
    val sendEnabled by remember { derivedStateOf { commentState.isValidate() } }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.surface
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = screenPadding,
                    vertical = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CommentTextField(
                modifier = Modifier.weight(1f),
                value = commentState.typedText,
                onValueChange = commentState::typeText
            )
            DoTSpacer(size = 10)
            SendIcon(
                enabled = sendEnabled,
                onClick = addComment
            )
        }
    }
}