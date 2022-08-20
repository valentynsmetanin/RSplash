package com.svapp.rsplash.ui.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.svapp.rsplash.databinding.FragmentRandomPhotoBinding
import com.svapp.rsplash.domain.model.PhotoDetails
import com.svapp.rsplash.utils.extensions.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Used for display random photo.
 */
@AndroidEntryPoint
class RandomPhotoFragment : Fragment() {

    private val viewModel: RandomPhotoViewModel by viewModels()
    private var _binding: FragmentRandomPhotoBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomPhotoBinding.inflate(inflater, container, false)
        observeUiState()

        return binding.root
    }

    private fun observeUiState() {
        launchAndRepeatWithViewLifecycle {
            viewModel.uiState.collect {
                updatePhotoUi(it.photo)
            }
        }
    }

    private fun updatePhotoUi(photo: PhotoDetails?) {
        with(binding) {
            imageViewPhoto.load(photo?.fullUrl)
            textViewAuthorName.text = photo?.userName
            textViewDescription.text = photo?.description
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
