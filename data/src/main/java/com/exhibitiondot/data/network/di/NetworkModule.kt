package com.exhibitiondot.data.network.di

import com.exhibitiondot.data.BuildConfig
import com.exhibitiondot.data.network.api.ApiConst
import com.exhibitiondot.data.network.retrofit.AuthInterceptor
import com.exhibitiondot.data.network.retrofit.CustomCallAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @Named("networkJson")
    fun provideNetworkJson(): Json
        = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            readTimeout(5_000, TimeUnit.MILLISECONDS)
            connectTimeout(5_000, TimeUnit.MILLISECONDS)
            addInterceptor(authInterceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("networkJson") json: Json,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder().apply {
            client(okHttpClient)
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(
                json.asConverterFactory(ApiConst.CONTENT_TYPE.toMediaType())
            )
            addCallAdapterFactory(CustomCallAdapter.Factory())
        }.build()

}