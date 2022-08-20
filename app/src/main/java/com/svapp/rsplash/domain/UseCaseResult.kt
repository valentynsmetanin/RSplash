package com.svapp.rsplash.domain

sealed class UseCaseResult<out T> {

    data class Success<out T>(val data: T) : UseCaseResult<T>()

    data class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}
