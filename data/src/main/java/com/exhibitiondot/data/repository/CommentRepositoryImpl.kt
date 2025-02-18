package com.exhibitiondot.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.exhibitiondot.data.datasource.comment.CommentDataSource
import com.exhibitiondot.data.mapper.toDomain
import com.exhibitiondot.data.mapper.toResult
import com.exhibitiondot.data.network.model.dto.CommentDto
import com.exhibitiondot.data.network.model.request.AddCommentRequest
import com.exhibitiondot.domain.model.Comment
import com.exhibitiondot.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentDataSource: CommentDataSource
) : CommentRepository {
    override fun getCommentList(eventId: Long): Flow<PagingData<Comment>> =
         commentDataSource
             .getCommentList(eventId)
             .map { pagingData -> pagingData.map(CommentDto::toDomain) }

    override suspend fun addComment(eventId: Long, content: String): Result<Unit> {
        val request = AddCommentRequest(content)
        return commentDataSource.addComment(eventId, request).toResult {  }
    }
}