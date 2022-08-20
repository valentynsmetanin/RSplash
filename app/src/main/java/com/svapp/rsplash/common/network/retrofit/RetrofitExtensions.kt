package com.svapp.rsplash.common.network.retrofit

import retrofit2.Retrofit

inline fun <reified T> Retrofit.createService(): T = this.create(T::class.java)
