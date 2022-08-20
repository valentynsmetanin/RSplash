package com.svapp.rsplash.common.network.retrofit.interceptor

import com.svapp.rsplash.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object SplashHeadersInterceptor : Interceptor {

    private const val HEADER_KEY_AUTH = "Authorization"
    private const val HEADER_KEY_VERSION = "Accept-Version"

    private const val HEADER_VALUE_AUTH_FORMATTER = "Client-ID %s"
    private const val HEADER_VALUE_VERSION = "V1"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader(HEADER_KEY_VERSION, HEADER_VALUE_VERSION)
            .addHeader(HEADER_KEY_AUTH, formatAuthHeader(BuildConfig.UNSPLASH_ACCESS_KEY))
            .build()

        return chain.proceed(request)
    }

    private fun formatAuthHeader(accessKey: String): String {
        return HEADER_VALUE_AUTH_FORMATTER.format(accessKey)
    }
}