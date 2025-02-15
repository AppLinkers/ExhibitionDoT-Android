package com.exhibitiondot.data.datasource.comment

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.exhibitiondot.data.network.api.CommentApi
import com.exhibitiondot.data.network.api.ApiConst
import com.exhibitiondot.data.model.dto.CommentDto
import com.exhibitiondot.data.network.NetworkState

class CommentPagingSource(
    private val commentApi: CommentApi,
    private val eventId: Long
) : PagingSource<Int, CommentDto>() {
    override fun getRefreshKey(state: PagingState<Int, CommentDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommentDto> {
        val pageNumber = params.key ?: ApiConst.DEFAULT_PAGE_NUMBER
        val response = commentApi.getCommentList(
            eventId = eventId,
            page = pageNumber,
            size = ApiConst.DEFAULT_PAGE_SIZE
        )
        return when (response) {
            is NetworkState.Success -> {
                val data = response.data
                val commentList = data.contents
                val prevKey = if (data.first) null else data.page -1
                val nextKey = if (data.last) null else data.page + 1
                LoadResult.Page(commentList, prevKey, nextKey)
            }
            is NetworkState.Failure -> {
                LoadResult.Invalid()
            }
            is NetworkState.NetworkError -> {
                LoadResult.Error(response.error)
            }
            is NetworkState.UnknownError -> {
                LoadResult.Error(response.t)
            }
        }
    }
}