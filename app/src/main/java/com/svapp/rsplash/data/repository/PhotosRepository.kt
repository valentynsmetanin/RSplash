package com.svapp.rsplash.data.repository

import com.svapp.rsplash.data.model.Photo

interface PhotosRepository {

    suspend fun getPhotosFeed(): List<Photo>

    suspend fun searchPhotos(query: String): List<Photo>

    suspend fun getRandomPhoto(): Photo

    suspend fun getPhoto(photoId: String): Photo
}