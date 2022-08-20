package com.svapp.rsplash.domain.mapper

import com.svapp.rsplash.common.di.DefaultDispatcher
import com.svapp.rsplash.data.model.Photo
import com.svapp.rsplash.domain.model.PhotoDetails
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class PhotoMapper @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun List<Photo?>.toDetails(): List<PhotoDetails> {
        return withContext(dispatcher) {
            mapNotNull {
                async { it.toDetails() } // Run transformations in concurrent execution
            }.awaitAll().filterNotNull()
        }
    }

    suspend fun Photo?.toDetails(): PhotoDetails? = withContext(dispatcher) {
        this@toDetails?.let { photo ->
            val id = photo.id
            val urls = photo.urls
            val regularUrl = photo.urls?.regular
            if (!id.isNullOrEmpty() && !regularUrl.isNullOrEmpty()) {
                PhotoDetails(
                    id = id,
                    url = regularUrl,
                    fullUrl = urls?.full,
                    userName = photo.user?.userName,
                    description = photo.description
                )
            } else {
                null
            }
        }
    }
}
