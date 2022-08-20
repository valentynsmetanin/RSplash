package com.svapp.rsplash.domain

import com.svapp.rsplash.utils.log.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in P, R>(
    private val dispatcher: CoroutineDispatcher,
    private val logger: Logger
) {

    suspend operator fun invoke(param: P): UseCaseResult<R> {
        return try {
            withContext(dispatcher) {
                execute(param).let {
                    UseCaseResult.Success(it)
                }
            }
        } catch (e: Exception) {
            logger.log(throwable = e)
            UseCaseResult.Error(e)
        }
    }

    protected abstract suspend fun execute(params: P): R
}
