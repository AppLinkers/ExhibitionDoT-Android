package com.exhibitiondot.data.datasource.event

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.exhibitiondot.data.network.api.EventApi
import com.exhibitiondot.data.network.api.ApiConst
import com.exhibitiondot.data.network.model.dto.EventDetailDto
import com.exhibitiondot.data.network.model.dto.EventDto
import com.exhibitiondot.data.network.NetworkState
import com.exhibitiondot.data.network.model.dto.EventInfoDto
import com.exhibitiondot.domain.model.EventParams
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class EventRemoteDataSource @Inject constructor(
    private val eventApi: EventApi
) : EventDataSource {
    override fun getEventList(params: EventParams): Flow<PagingData<EventDto>> =
        Pager(
            config = PagingConfig(pageSize = ApiConst.DEFAULT_PAGE_SIZE)
        ){
            EventPagingSource(
                eventApi = eventApi,
                eventParams = params
            )
        }.flow

    override suspend fun getEventDetail(eventId: Long): NetworkState<EventDetailDto> {
        return eventApi.getEventDetail(eventId)
    }

    override suspend fun toggleEventLike(eventId: Long): NetworkState<Unit> {
        return eventApi.toggleEventLike(eventId)
    }

    override suspend fun addEvent(file: File, eventInfo: EventInfoDto): NetworkState<Unit> {
        return eventApi.addEvent(
            file = file.toMultipartFile("file"),
            event = eventInfo.toJsonRequestBody()
        )
    }

    override suspend fun updateEvent(
        file: File?,
        eventInfo: EventInfoDto?,
        eventId: Long
    ): NetworkState<Unit> {
        return eventApi.updateEvent(
            file = file?.toMultipartFile("file"),
            event = eventInfo?.toJsonRequestBody(),
            eventId = eventId
        )
    }

    override suspend fun deleteEvent(eventId: Long): NetworkState<Unit> {
        return eventApi.deleteEvent(eventId)
    }

    private fun File.toMultipartFile(key: String): MultipartBody.Part {
       return MultipartBody.Part.createFormData(
           name = key,
           filename = name,
           body = asRequestBody("image/*".toMediaTypeOrNull())
       )
    }

    private fun EventInfoDto.toJsonRequestBody(): RequestBody {
        val json = Json.encodeToString(this)
        return json.toRequestBody(ApiConst.CONTENT_TYPE.toMediaTypeOrNull())
    }
}