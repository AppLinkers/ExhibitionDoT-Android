package com.exhibitiondot.data.mapper

import com.exhibitiondot.data.model.dto.CommentDto
import com.exhibitiondot.domain.model.Comment

fun CommentDto.toDomain() =
    Comment(
        id = id,
        authorNickname = authorNickname,
        content = content,
        createdAt = createdAt
    )