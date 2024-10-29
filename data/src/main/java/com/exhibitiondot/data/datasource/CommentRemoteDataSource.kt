package com.exhibitiondot.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.exhibitiondot.data.api.CommentApi
import com.exhibitiondot.data.constant.ApiConst
import com.exhibitiondot.data.model.dto.CommentDto
import com.exhibitiondot.data.model.request.AddCommentRequest
import com.exhibitiondot.data.network.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(
    private val commentApi: CommentApi
) : CommentDataSource {
    override fun getCommentList(eventId: Long): Flow<PagingData<CommentDto>> =
        Pager(
            config = PagingConfig(pageSize = ApiConst.DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                CommentPagingSource(
                    commentApi = commentApi,
                    eventId = eventId
                )
            }
        ).flow

    override suspend fun addComment(
        eventId: Long,
        addCommentRequest: AddCommentRequest
    ): NetworkState<Unit> {
        return commentApi.addComment(eventId, addCommentRequest)
    }
}