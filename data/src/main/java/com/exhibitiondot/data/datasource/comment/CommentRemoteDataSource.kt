package com.exhibitiondot.data.datasource.comment

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.exhibitiondot.data.network.api.CommentApi
import com.exhibitiondot.data.network.api.ApiConst
import com.exhibitiondot.data.network.model.dto.CommentDto
import com.exhibitiondot.data.network.model.request.AddCommentRequest
import com.exhibitiondot.data.network.NetworkState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(
    private val commentApi: CommentApi
) : CommentDataSource {
    override fun getCommentList(eventId: Long): Flow<PagingData<CommentDto>> =
        Pager(
            config = PagingConfig(pageSize = ApiConst.DEFAULT_PAGE_SIZE),
        ) {
            CommentPagingSource(
                commentApi = commentApi,
                eventId = eventId
            )
        }.flow

    override suspend fun addComment(
        eventId: Long,
        addCommentRequest: AddCommentRequest
    ): NetworkState<Unit> {
        return commentApi.addComment(eventId, addCommentRequest)
    }
}