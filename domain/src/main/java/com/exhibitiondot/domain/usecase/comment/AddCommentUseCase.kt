package com.exhibitiondot.domain.usecase.comment

import com.exhibitiondot.domain.repository.CommentRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository,
) {
    suspend operator fun invoke(eventId: Long, comment: String): Result<Unit> =
        commentRepository.addComment(eventId, comment)
}