package com.svapp.rsplash.common.di

import com.svapp.rsplash.BuildConfig
import com.svapp.rsplash.common.network.retrofit.RSplashRetrofitFactory
import com.svapp.rsplash.common.network.retrofit.RetrofitFactory
import com.svapp.rsplash.common.network.retrofit.config.BaseUrl
import com.svapp.rsplash.common.network.retrofit.config.RetrofitConfig
import com.svapp.rsplash.common.network.retrofit.createService
import com.svapp.rsplash.data.network.UnsplashApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModules {

    @Singleton
    @Provides
    fun provideBaseUrl(): BaseUrl = BaseUrl(url = BuildConfig.UNSPLASH_API_URL)

    // TODO: create provide method to provide converterFactory and isDebug separately
    @Singleton
    @Provides
    fun provideRetrofitConfig(baseUrl: BaseUrl): RetrofitConfig = RetrofitConfig(
        baseUrl = baseUrl,
        converterFactory = GsonConverterFactory.create(),
        isDebugMode = BuildConfig.DEBUG
    )

    @Singleton
    @Provides
    fun provideRetrofitFactory(): RetrofitFactory = RSplashRetrofitFactory

    @Singleton
    @Provides
    fun provideRetrofit(
        config: RetrofitConfig,
        factory: RetrofitFactory
    ): Retrofit = factory.create(config)

    @Singleton
    @Provides
    fun provideUnsplashApiService(retrofit: Retrofit): UnsplashApiService = retrofit.createService()
}
