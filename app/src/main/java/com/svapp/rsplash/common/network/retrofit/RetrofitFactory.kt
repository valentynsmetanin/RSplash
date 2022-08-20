package com.svapp.rsplash.common.network.retrofit

import com.svapp.rsplash.common.network.retrofit.config.RetrofitConfig
import retrofit2.Retrofit

interface RetrofitFactory {

    fun create(config: RetrofitConfig): Retrofit
}
