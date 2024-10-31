package com.exhibitiondot.domain.usecase.comment

import androidx.paging.PagingData
import com.exhibitiondot.domain.model.Comment
import com.exhibitiondot.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentListUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {
    operator fun invoke(eventId: Long): Flow<PagingData<Comment>> =
        commentRepository.getCommentList(eventId)
}