package com.exhibitiondot.data.model.response

import com.exhibitiondot.data.model.dto.CommentDto
import kotlinx.serialization.Serializable

@Serializable
data class CommentListResponse(
    val contents: List<CommentDto>,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val first: Boolean,
    val last: Boolean
)
