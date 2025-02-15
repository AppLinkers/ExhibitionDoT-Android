package com.exhibitiondot.data.datasource.di

import com.exhibitiondot.data.datasource.comment.CommentDataSource
import com.exhibitiondot.data.datasource.comment.CommentRemoteDataSource
import com.exhibitiondot.data.datasource.event.EventDataSource
import com.exhibitiondot.data.datasource.event.EventRemoteDataSource
import com.exhibitiondot.data.datasource.user.UserDataSource
import com.exhibitiondot.data.datasource.user.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    @Singleton
    fun bindsUserDataSource(
        userDataSource: UserRemoteDataSource
    ) : UserDataSource

    @Binds
    @Singleton
    fun bindsEventDataSource(
        eventDataSource: EventRemoteDataSource
    ) : EventDataSource

    @Binds
    @Singleton
    fun bindsCommentDataSource(
        commentDataSource: CommentRemoteDataSource
    ) : CommentDataSource
}