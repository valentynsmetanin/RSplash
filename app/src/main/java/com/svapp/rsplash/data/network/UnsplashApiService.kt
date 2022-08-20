package com.svapp.rsplash.data.network

import com.svapp.rsplash.data.model.Photo
import com.svapp.rsplash.data.model.SearchPhoto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApiService {

    /**
     * Get a single page from the Editorial feed.
     * @return random list of photos
     */
    @GET("/photos")
    suspend fun getPhotosFeed(): List<Photo>

    /**
     * @return pictures by search query
     */
    @GET("/search/photos")
    suspend fun searchPhotos(@Query("query") query: String): SearchPhoto

    /**
     * Retrieve a single random photo, given optional filters.
     * @return random photo
     */
    @GET("/photos/random")
    suspend fun getRandomPhoto(): Photo

    /**
     * Retrieve a single photo.
     * @return photo
     */
    @GET("/photos/{photoId}")
    suspend fun getPhoto(@Path("photoId") photoId: String): Photo
}
