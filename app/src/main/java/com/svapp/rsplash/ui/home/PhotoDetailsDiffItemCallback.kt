package com.svapp.rsplash.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.svapp.rsplash.domain.model.PhotoDetails

object PhotoDetailsDiffItemCallback : DiffUtil.ItemCallback<PhotoDetails>() {

    override fun areItemsTheSame(oldItem: PhotoDetails, newItem: PhotoDetails): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoDetails, newItem: PhotoDetails): Boolean {
        return oldItem.id == newItem.id && oldItem.url == newItem.url
    }
}
