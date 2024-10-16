package com.exhibitiondot.domain.repository

import androidx.paging.PagingData
import com.exhibitiondot.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getCommentList(eventId: Long): Flow<PagingData<Comment>>

    suspend fun addComment(eventId: Long, content: String): Result<Unit>
}