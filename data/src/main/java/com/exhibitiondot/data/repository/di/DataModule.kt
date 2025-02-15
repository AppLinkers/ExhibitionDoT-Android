package com.exhibitiondot.data.repository.di

import com.exhibitiondot.data.repository.CommentRepositoryImpl
import com.exhibitiondot.data.repository.EventRepositoryImpl
import com.exhibitiondot.data.repository.PreferenceRepositoryImpl
import com.exhibitiondot.data.repository.UserRepositoryImpl
import com.exhibitiondot.domain.repository.CommentRepository
import com.exhibitiondot.domain.repository.EventRepository
import com.exhibitiondot.domain.repository.PreferenceRepository
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

    @Binds
    @Singleton
    fun bindsPreferenceRepository(
        preferenceRepository: PreferenceRepositoryImpl
    ) : PreferenceRepository
}