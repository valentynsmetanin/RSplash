package com.svapp.rsplash.ui.photodetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import coil.load
import com.svapp.rsplash.R
import com.svapp.rsplash.databinding.FragmentPhotoDetailsBinding
import com.svapp.rsplash.domain.model.PhotoDetails
import com.svapp.rsplash.utils.extensions.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Displays photo details by photo id.
 */
@AndroidEntryPoint
class PhotoDetailsFragment : Fragment() {

    private val viewModel: PhotoDetailsViewModel by viewModels()
    private var _binding: FragmentPhotoDetailsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
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