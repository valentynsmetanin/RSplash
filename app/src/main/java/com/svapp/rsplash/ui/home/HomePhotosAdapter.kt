package com.svapp.rsplash.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.svapp.rsplash.databinding.ItemHomePhotoBinding
import com.svapp.rsplash.domain.model.PhotoDetails

class HomePhotosAdapter(
    private val onPhotoClick: (category: PhotoDetails) -> Unit
) : ListAdapter<PhotoDetails, HomePhotosAdapter.ViewHolder>(
    PhotoDetailsDiffItemCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomePhotoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding) { position ->
            val category = getItem(position)
            onPhotoClick.invoke(category)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHomePhotoBinding,
        private val onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

        fun bind(item: PhotoDetails) {
            with(binding) {
                imageViewPhoto.load(item.url)
                /* we can skip text null check for TextView,
                   in case of null it will set empty String. */
                textViewUserName.text = item.userName
            }
        }
    }
}
