package com.exhibitiondot.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.exhibitiondot.data.api.EventApi
import com.exhibitiondot.data.constant.ApiConst
import com.exhibitiondot.data.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState

class EventPagingSource(
    private val eventApi: EventApi,
    private val region: String?,
    private val categoryList: List<String>,
    private val eventTypeList: List<String>,
    private val query: String
) : PagingSource<Int, EventDto>() {
    override fun getRefreshKey(state: PagingState<Int, EventDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EventDto> {
        val pageNumber = params.key ?: ApiConst.DEFAULT_PAGE_NUMBER
        val response = eventApi.getEventList(
            page = pageNumber,
            size = ApiConst.DEFAULT_PAGE_SIZE,
            region = region,
            categoryList = categoryList.ifEmpty { null },
            eventTypeList = eventTypeList.ifEmpty { null },
            query = query.ifEmpty { null }
        )
        return when (response) {
            is NetworkState.Success -> {
                val eventList = response.data.contents
                Log.d("pagingSource에서", eventList.size.toString())
                val prevKey = if (pageNumber == ApiConst.DEFAULT_PAGE_NUMBER) null else pageNumber -1
                val nextKey = if (eventList.isEmpty()) null else pageNumber + 1
                LoadResult.Page(eventList, prevKey, nextKey)
            }
            is NetworkState.Failure -> {
                Log.d("pagingSource에서", "failure")
                LoadResult.Invalid()
            }
            is NetworkState.NetworkError -> {
                Log.d("pagingSource에서", "networkError")
                LoadResult.Error(response.error)
            }
            is NetworkState.UnknownError -> {
                Log.d("pagingSource에서", response.t.message ?: "메세지도 없음")
                LoadResult.Error(response.t)
            }
        }
    }
}