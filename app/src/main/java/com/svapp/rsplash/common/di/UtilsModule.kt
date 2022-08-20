package com.svapp.rsplash.common.di

import com.svapp.rsplash.utils.log.Logger
import com.svapp.rsplash.utils.log.logcat.LogcatLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {

    @Singleton
    @Binds
    abstract fun bindLogger(logger: LogcatLogger): Logger
}
