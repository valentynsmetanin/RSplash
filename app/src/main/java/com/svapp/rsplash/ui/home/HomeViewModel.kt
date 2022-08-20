package com.svapp.rsplash.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svapp.rsplash.domain.UseCaseResult
import com.svapp.rsplash.domain.model.PhotoDetails
import com.svapp.rsplash.domain.photos.GetPhotosFeedUseCase
import com.svapp.rsplash.domain.photos.SearchPhotosUseCase
import com.svapp.rsplash.utils.extensions.tryOffer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPhotosFeedUseCase: GetPhotosFeedUseCase,
    private val searchPhotosUseCase: SearchPhotosUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: Flow<UiState> =
        _uiState.stateIn(viewModelScope, SharingStarted.Lazily, _uiState.value)

    private val _searchQuery: MutableStateFlow<CharSequence?> = MutableStateFlow(null)

    // SIDE EFFECTS: Navigation actions
    private val _navigationActions = Channel<HomeNavigationAction>(capacity = Channel.CONFLATED)

    // Exposed with receiveAsFlow to make sure that only one observer receives updates.
    val navigationActions = _navigationActions.receiveAsFlow()

    init {
        refreshPhotos()
    }

    fun onPhotoClick(photo: PhotoDetails) {
        HomeNavigationAction.NavigateToPhotoDetails(photo.id).run {
            _navigationActions.tryOffer(this)
        }
    }

    // TODO: Add debounce
    fun onSearchQueryChanged(query: CharSequence?) {
        _searchQuery.update { query }
        refreshPhotos()
    }

    fun refreshPhotos() {
        viewModelScope.launch {
            val query = _searchQuery.firstOrNull()
            fetchPhotos(query)
        }
    }

    private fun fetchPhotos(query: CharSequence?) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = if (query.isNullOrEmpty()) {
                getFeedPhotos()
            } else {
                searchPhotos(query)
            }
            updateUiState(result)
        }
    }

    private suspend fun getFeedPhotos() = getPhotosFeedUseCase.invoke(Unit)

    private suspend fun searchPhotos(
        query: CharSequence
    ) = searchPhotosUseCase.invoke(query.toString())

    private fun updateUiState(result: UseCaseResult<List<PhotoDetails>>) {
        _uiState.update { state ->
            when (result) {
                is UseCaseResult.Error -> {
                    state.copy(error = result.exception.message, isLoading = false)
                }
                is UseCaseResult.Success -> {
                    state.copy(photos = result.data, isLoading = false)
                }
            }
        }
    }

    data class UiState(
        val photos: List<PhotoDetails> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )
}
