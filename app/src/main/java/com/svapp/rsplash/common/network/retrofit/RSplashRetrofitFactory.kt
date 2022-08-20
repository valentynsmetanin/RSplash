package com.svapp.rsplash.common.network.retrofit

import com.svapp.rsplash.common.network.retrofit.config.RetrofitConfig
import com.svapp.rsplash.common.network.retrofit.interceptor.SplashHeadersInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RSplashRetrofitFactory : RetrofitFactory {

    override fun create(config: RetrofitConfig): Retrofit = Retrofit.Builder().apply {
        baseUrl(config.baseUrl.url)
        config.converterFactory?.let {
            addConverterFactory(it)
        }

        val okHttpClient = createOkHttpClient(config.isDebugMode)
        client(okHttpClient)
    }.build()

    private fun createOkHttpClient(isDebugMode: Boolean): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // Set logging interceptor only for debug build type
        if (isDebugMode) {
            val loggingInterceptor = createLoggingInterceptor()
            builder.addInterceptor(loggingInterceptor)
        }

        // Add Authorization to all API methods
        builder.addInterceptor(SplashHeadersInterceptor)

        return builder.build()
    }

    private fun createLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
