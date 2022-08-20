package com.svapp.rsplash.data.repository

import com.svapp.rsplash.data.model.Photo
import com.svapp.rsplash.data.network.UnsplashApiService
import javax.inject.Inject

class DefaultPhotosRepository @Inject constructor(
    private val apiService: UnsplashApiService
) : PhotosRepository {

    override suspend fun getPhotosFeed(): List<Photo> {
        return apiService.getPhotosFeed()
    }

    override suspend fun searchPhotos(query: String): List<Photo> {
        return apiService.searchPhotos(query).results
    }

    override suspend fun getRandomPhoto(): Photo = apiService.getRandomPhoto()

    override suspend fun getPhoto(photoId: String): Photo {
        return apiService.getPhoto(photoId)
    }
}
