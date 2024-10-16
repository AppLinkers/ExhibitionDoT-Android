package com.exhibitiondot.data.api

import com.exhibitiondot.data.model.request.AddCommentRequest
import com.exhibitiondot.data.model.response.CommentListResponse
import com.exhibitiondot.data.network.NetworkState
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApi {
    @GET("event/{event-id}/comment")
    suspend fun getCommentList(
        @Path("event-id") eventId: Long,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): NetworkState<CommentListResponse>

    @POST("event/{event-id}/comment")
    suspend fun addComment(
        @Path("event-id") eventId: Long,
        @Body addCommentRequest: AddCommentRequest
    ): NetworkState<Void>
}