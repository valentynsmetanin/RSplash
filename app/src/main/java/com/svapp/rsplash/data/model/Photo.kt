package com.svapp.rsplash.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    val id: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    val urls: Urls?,
    val user: PhotoUser?
)