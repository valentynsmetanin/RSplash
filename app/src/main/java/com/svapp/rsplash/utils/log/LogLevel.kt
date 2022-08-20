package com.svapp.rsplash.utils.log

import androidx.annotation.IntDef

@IntDef(
    LogLevel.VERBOSE,
    LogLevel.DEBUG,
    LogLevel.INFO,
    LogLevel.WARNING,
    LogLevel.ERROR,
    LogLevel.ASSERT
)
annotation class LogLevel {
    companion object {
        const val VERBOSE = 1
        const val DEBUG = 2
        const val INFO = 3
        const val WARNING = 4
        const val ERROR = 5
        const val ASSERT = 6
    }
}
