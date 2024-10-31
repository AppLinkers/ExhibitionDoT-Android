package com.exhibitiondot.presentation.ui.screen.main.eventDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.exhibitiondot.presentation.model.CommentUiModel
import com.exhibitiondot.presentation.model.EventDetailUiModel
import com.exhibitiondot.presentation.ui.component.DoTImage
import com.exhibitiondot.presentation.ui.component.DoTLoadingScreen
import com.exhibitiondot.presentation.ui.component.EventDetailTopBar
import com.exhibitiondot.presentation.ui.component.HeartIcon
import com.exhibitiondot.presentation.ui.theme.screenPadding

@Composable
fun EventDetailRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val commentList = viewModel.commentList.collectAsLazyPagingItems()

    when (uiState) {
        EventDetailUiState.Loading -> DoTLoadingScreen(
            modifier = modifier
        )
        EventDetailUiState.Failure -> {}
        is EventDetailUiState.Success -> EventDetailScreen(
            modifier = modifier,
            uiState = uiState as EventDetailUiState.Success,
            commentList = commentList,
            toggleEventLike = viewModel::toggleEventLike,
            onBack = onBack
        )
    }
    BackHandler(onBack = onBack)
}

@Composable
private fun EventDetailScreen(
    modifier: Modifier,
    uiState: EventDetailUiState.Success,
    commentList: LazyPagingItems<CommentUiModel>,
    toggleEventLike: (EventDetailUiModel) -> Unit,
    onBack: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val skipImage by remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset > 420 } }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
        ) {
            item {
                EventDetailView(
                    eventDetail = uiState.eventDetail,
                    toggleEventLike = toggleEventLike
                )
            }
            items(
                count = commentList.itemCount,
                key = { index -> commentList[index]?.id ?: index }
            ) { index ->
                commentList[index]?.let { comment ->
                    CommentItem(comment = comment)
                }
            }
        }
        EventDetailTopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            eventName = uiState.eventDetail.name,
            skipImage = skipImage,
            onBack = onBack
        )
    }
}

@Composable
private fun EventDetailView(
    modifier: Modifier = Modifier,
    eventDetail: EventDetailUiModel,
    toggleEventLike: (EventDetailUiModel) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DoTImage(
            modifier = Modifier
                .fillMaxSize()
                .height(420.dp),
            url = "https://www.it-b.co.kr/news/photo/202011/45197_42822_152.png",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = screenPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "2024 Naver Corp. 컨퍼런스 (코엑스 컨벤션 홀)",
                    style = MaterialTheme.typography.titleLarge
                )
                HeartIcon(
                    modifier = Modifier.clickable(
                        onClick = { toggleEventLike(eventDetail) }
                    ),
                    size = 28,
                    isLike = eventDetail.isLike
                )
            }
        }
    }
}

@Composable
private fun CommentItem(
    modifier: Modifier = Modifier,
    comment: CommentUiModel
) {

}