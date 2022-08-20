package com.svapp.rsplash.domain.photo

import com.svapp.rsplash.common.di.DefaultDispatcher
import com.svapp.rsplash.data.repository.PhotosRepository
import com.svapp.rsplash.domain.UseCase
import com.svapp.rsplash.domain.mapper.PhotoMapper
import com.svapp.rsplash.domain.model.PhotoDetails
import com.svapp.rsplash.utils.log.Logger
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class GetPhotoUseCase @Inject constructor(
    @DefaultDispatcher dispatcher: CoroutineDispatcher,
    logger: Logger,
    private val repository: PhotosRepository,
    private val mapper: PhotoMapper
) : UseCase<String, PhotoDetails?>(
    dispatcher = dispatcher,
    logger = logger
) {

    override suspend fun execute(params: String): PhotoDetails? {
        return repository.getPhoto(params).let { photo ->
            with(mapper) { photo.toDetails() }
        }
    }
}
