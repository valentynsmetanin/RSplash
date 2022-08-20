package com.svapp.rsplash.common.network.retrofit.config

import retrofit2.Converter

data class RetrofitConfig(
    val baseUrl: BaseUrl,
    val converterFactory: Converter.Factory?,
    val isDebugMode: Boolean
)
