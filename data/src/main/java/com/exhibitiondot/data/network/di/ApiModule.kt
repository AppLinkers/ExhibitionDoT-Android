package com.exhibitiondot.data.network.di

import com.exhibitiondot.data.network.api.CommentApi
import com.exhibitiondot.data.network.api.EventApi
import com.exhibitiondot.data.network.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create()

    @Provides
    @Singleton
    fun provideEventApi(retrofit: Retrofit): EventApi = retrofit.create()

    @Provides
    @Singleton
    fun provideCommentApi(retrofit: Retrofit): CommentApi = retrofit.create()
}