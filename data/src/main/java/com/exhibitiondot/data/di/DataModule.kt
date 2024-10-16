package com.exhibitiondot.data.di

import com.exhibitiondot.data.datasource.CommentDataSource
import com.exhibitiondot.data.datasource.CommentRemoteDataSource
import com.exhibitiondot.data.datasource.EventDataSource
import com.exhibitiondot.data.datasource.EventRemoteDataSource
import com.exhibitiondot.data.datasource.UserDataSource
import com.exhibitiondot.data.datasource.UserRemoteDataSource
import com.exhibitiondot.data.repository.CommentRepositoryImpl
import com.exhibitiondot.data.repository.EventRepositoryImpl
import com.exhibitiondot.data.repository.UserRepositoryImpl
import com.exhibitiondot.domain.repository.CommentRepository
import com.exhibitiondot.domain.repository.EventRepository
import com.exhibitiondot.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
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

    @Binds
    @Singleton
    fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ) : UserRepository

    @Binds
    @Singleton
    fun bindsEventRepository(
        eventRepository: EventRepositoryImpl
    ) : EventRepository

    @Binds
    @Singleton
    fun bindsCommentRepository(
        commentRepository: CommentRepositoryImpl
    ) : CommentRepository
}