package com.exhibitiondot.data.datasource.comment

import androidx.paging.PagingData
import com.exhibitiondot.data.model.dto.CommentDto
import com.exhibitiondot.data.model.request.AddCommentRequest
import com.exhibitiondot.data.network.NetworkState
import kotlinx.coroutines.flow.Flow

interface CommentDataSource {
    fun getCommentList(eventId: Long): Flow<PagingData<CommentDto>>

    suspend fun addComment(
        eventId: Long,
        addCommentRequest: AddCommentRequest
    ): NetworkState<Unit>
}