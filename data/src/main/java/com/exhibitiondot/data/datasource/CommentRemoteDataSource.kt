package com.exhibitiondot.data.datasource

import com.exhibitiondot.data.api.CommentApi
import com.exhibitiondot.data.model.dto.CommentDto
import com.exhibitiondot.data.model.request.AddCommentRequest
import com.exhibitiondot.data.network.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CommentRemoteDataSource(
    private val commentApi: CommentApi
) : CommentDataSource {
    override fun getCommentList(
        eventId: Long,
        page: Int?,
        size: Int?
    ): Flow<List<CommentDto>> = flow {
        val response = commentApi.getCommentList(eventId, page, size)
        when (response) {
            is NetworkState.Success -> emit(response.data.contents)
            else -> emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addComment(
        eventId: Long,
        addCommentRequest: AddCommentRequest
    ): NetworkState<Unit> {
        return commentApi.addComment(eventId, addCommentRequest)
    }
}