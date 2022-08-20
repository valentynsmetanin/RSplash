package com.svapp.rsplash.utils.log.logcat

import android.util.Log
import com.svapp.rsplash.utils.log.LogLevel
import com.svapp.rsplash.utils.log.Logger
import javax.inject.Inject

class LogcatLogger @Inject constructor() : Logger {

    // TODO: Add tag implementation
    override fun log(@LogLevel level: Int, message: String) {
        when (level) {
            LogLevel.VERBOSE -> Log.v(null, message)
            LogLevel.DEBUG -> Log.d(null, message)
            LogLevel.INFO -> Log.i(null, message)
            LogLevel.WARNING -> Log.w(null, message)
            LogLevel.ERROR, LogLevel.ASSERT -> Log.e(null, message)
        }
    }

    // TODO: Add tag implementation
    override fun log(@LogLevel level: Int, throwable: Throwable) {
        when (level) {
            LogLevel.VERBOSE -> Log.v(null, null, throwable)
            LogLevel.DEBUG -> Log.d(null, null, throwable)
            LogLevel.INFO -> Log.i(null, null, throwable)
            LogLevel.WARNING -> Log.w(null, null, throwable)
            LogLevel.ERROR, LogLevel.ASSERT -> Log.e(null, null, throwable)
        }
    }
}
