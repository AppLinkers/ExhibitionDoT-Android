package com.exhibitiondot.data.network.api

import com.exhibitiondot.data.network.model.dto.EventDetailDto
import com.exhibitiondot.data.network.model.response.EventListResponse
import com.exhibitiondot.data.network.NetworkState
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {
    @GET("event")
    suspend fun getEventList(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("region") region: String? = null,
        @Query("category-list") categoryList: List<String>? = null,
        @Query("event-type-list") eventTypeList: List<String>? = null,
        @Query("query") query: String? = null
    ): NetworkState<EventListResponse>

    @GET("event/{event-id}")
    suspend fun getEventDetail(@Path("event-id") eventId: Long): NetworkState<EventDetailDto>

    @POST("event/{event-id}/like")
    suspend fun toggleEventLike(@Path("event-id") eventId: Long): NetworkState<Unit>

    @Multipart
    @POST("event")
    suspend fun addEvent(
        @Part file: MultipartBody.Part,
        @Part("event") event: RequestBody
    ) : NetworkState<Unit>

    @Multipart
    @PATCH("event/{event-id}")
    suspend fun updateEvent(
        @Part file: MultipartBody.Part? = null,
        @Part("event") event: RequestBody,
        @Path("event-id") eventId: Long
    ) : NetworkState<Unit>

    @DELETE("event/{event-id}")
    suspend fun deleteEvent(@Path("event-id") eventId: Long) : NetworkState<Unit>
}