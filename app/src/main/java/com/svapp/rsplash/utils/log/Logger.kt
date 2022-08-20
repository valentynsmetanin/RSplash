package com.svapp.rsplash.utils.log

interface Logger {

    fun log(@LogLevel level: Int = LogLevel.VERBOSE, message: String)

    fun log(@LogLevel level: Int = LogLevel.VERBOSE, throwable: Throwable)
}
