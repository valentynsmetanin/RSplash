package com.svapp.rsplash.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.svapp.rsplash.R
import com.svapp.rsplash.databinding.FragmentHomeBinding
import com.svapp.rsplash.ui.utils.afterTextChangedAsFlow
import com.svapp.rsplash.ui.utils.recyclerview.GridOffsetItemDecoration
import com.svapp.rsplash.utils.extensions.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

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
        setClickListeners()
        setSearchTextChangeListener()
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

    private fun setClickListeners() {
        with(binding) {
            swipeRefreshHome.setOnRefreshListener {
                viewModel.refreshPhotos()
            }
            fabRandomPhoto.setOnClickListener {
                viewModel.onRandomClick()
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setSearchTextChangeListener() {
        launchAndRepeatWithViewLifecycle {
            binding.editTextSearch
                .afterTextChangedAsFlow()
                .debounce(SEARCH_DEBOUNCE_MILLIS) // Avoid fast user input
                .collect {
                    viewModel.onSearchQueryChanged(it)
                }
        }
    }

    private fun observeUiState() {
        launchAndRepeatWithViewLifecycle {
            viewModel.uiState.collect {
                binding.swipeRefreshHome.isRefreshing = it.isLoading
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
                    is HomeNavigationAction.NavigateToRandomPhoto -> {
                        navigateRandomPhoto()
                    }
                }
            }
        }
    }

    private fun navigatePhotoDetails(photoId: String) {
        HomeFragmentDirections.actionHomeFragmentToPhotoDetailsFragment(photoId).run {
            findNavController().navigate(this)
        }
    }

    private fun navigateRandomPhoto() {
        HomeFragmentDirections.actionHomeFragmentToRandomPhotoFragment().run {
            findNavController().navigate(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        private const val SEARCH_DEBOUNCE_MILLIS = 500L
    }
}
