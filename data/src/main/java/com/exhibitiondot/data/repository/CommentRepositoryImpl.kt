package com.exhibitiondot.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.exhibitiondot.data.api.CommentApi
import com.exhibitiondot.data.constant.ApiConst
import com.exhibitiondot.data.datasource.CommentDataSource
import com.exhibitiondot.data.datasource.CommentPagingSource
import com.exhibitiondot.data.mapper.toDomain
import com.exhibitiondot.data.model.dto.CommentDto
import com.exhibitiondot.data.model.request.AddCommentRequest
import com.exhibitiondot.data.network.NetworkState
import com.exhibitiondot.domain.model.Comment
import com.exhibitiondot.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CommentRepositoryImpl(
    private val commentApi: CommentApi,
    private val commentDataSource: CommentDataSource
) : CommentRepository {
    override fun getCommentList(eventId: Long): Flow<PagingData<Comment>> =
        Pager(
            config = PagingConfig(pageSize = ApiConst.DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                CommentPagingSource(
                    commentApi = commentApi,
                    eventId = eventId
                )
            }
        ).flow.map { pagingData -> pagingData.map(CommentDto::toDomain) }

    override suspend fun addComment(eventId: Long, content: String): Result<Unit> {
        val response = commentDataSource.addComment(
            eventId = eventId,
            addCommentRequest = AddCommentRequest(content)
        )
        return when (response) {
            is NetworkState.Success -> Result.success(Unit)
            else -> Result.failure(IllegalStateException())
        }
    }
}