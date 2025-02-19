package com.exhibitiondot.presentation.mapper

import com.exhibitiondot.domain.model.Comment
import com.exhibitiondot.presentation.model.CommentUiModel

fun Comment.toUiModel() =
    CommentUiModel(
        id = id,
        nickname = "@$authorNickname",
        content = content,
        createdAt = format(DateFormatStrategy.RelativeTime(createdAt))
    )