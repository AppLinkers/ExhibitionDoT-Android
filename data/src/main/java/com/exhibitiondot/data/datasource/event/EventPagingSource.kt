package com.exhibitiondot.data.datasource.event

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.exhibitiondot.data.network.api.EventApi
import com.exhibitiondot.data.network.api.ApiConst
import com.exhibitiondot.data.network.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import com.exhibitiondot.domain.model.EventParams

class EventPagingSource(
    private val eventApi: EventApi,
    private val eventParams: EventParams,
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
            region = eventParams.region?.key,
            categoryList = eventParams.categoryList.map { it.key },
            eventTypeList = eventParams.eventTypeList.map { it.key },
            query = eventParams.query
        )
        return when (response) {
            is NetworkState.Success -> {
                val eventList = response.data.contents
                val prevKey = if (pageNumber == ApiConst.DEFAULT_PAGE_NUMBER) null else pageNumber -1
                val nextKey = if (eventList.isEmpty()) null else pageNumber + 1
                LoadResult.Page(eventList, prevKey, nextKey)
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