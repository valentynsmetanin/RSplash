package com.svapp.rsplash.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.svapp.rsplash.R
import com.svapp.rsplash.ui.utils.recyclerview.GridOffsetItemDecoration
import com.svapp.rsplash.utils.extensions.launchAndRepeatWithViewLifecycle
import com.svapp.rsplash.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 * The entry point to the app.
 */
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val photosAdapter = HomePhotosAdapter { photo ->
        viewModel.onPhotoClick(photo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupCategoriesRecyclerView()
        observeUiState()
        observeNavigationActions()

        return binding.root
    }

    private fun setupCategoriesRecyclerView() {
        with(binding.recyclerViewPhotos) {
            val numberOfColumns = resources.getInteger(R.integer.home_photos_columns_count)
            layoutManager = GridLayoutManager(requireActivity(), numberOfColumns)
            adapter = this@HomeFragment.photosAdapter
            val itemDecoration =
                GridOffsetItemDecoration(R.dimen.default_margin_half, numberOfColumns)
            addItemDecoration(itemDecoration)
        }
    }

    private fun observeUiState() {
        launchAndRepeatWithViewLifecycle {
            viewModel.uiState.collect {
                // binding.swipeRefreshHome.isRefreshing = it.isLoading
                photosAdapter.submitList(it.photos)
            }
        }
    }

    private fun observeNavigationActions() {
        launchAndRepeatWithViewLifecycle {
            viewModel.navigationActions.collect { action ->
                when (action) {
                    is HomeNavigationAction.NavigateToPhotoDetails -> {
                        navigatePhotoDetails(action.photoId)
                    }
                }
            }
        }
    }

    private fun navigatePhotoDetails(photoId: String) {
        // TODO: navigate to details
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}