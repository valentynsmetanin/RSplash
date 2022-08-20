package com.svapp.rsplash.ui.photodetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svapp.rsplash.domain.UseCaseResult
import com.svapp.rsplash.domain.model.PhotoDetails
import com.svapp.rsplash.domain.photo.GetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    // Hilt & Navigation Component allows to get args direct from savedStateHandle
    private val savedStateHandle: SavedStateHandle,
    private val getPhotoUseCase: GetPhotoUseCase
) : ViewModel() {

    private val _photoId: MutableSharedFlow<String?> = MutableSharedFlow<String?>(replay = 1)
        .apply {
            // Emit initial value without suspend
            tryEmit(getPhotoIdArg())
        }

    val uiState: Flow<UiState> =
        _photoId.filterNotNull().flatMapLatest { photoId ->
            flow {
                emit(UiState(isLoading = true))
                val state = when (val result = getPhotoUseCase.invoke(photoId)) {
                    is UseCaseResult.Error -> {
                        UiState(error = result.exception.message, isLoading = false)
                    }
                    is UseCaseResult.Success -> {
                        UiState(photo = result.data, isLoading = false)
                    }
                }
                emit(state)
            }.catch { throwable ->
                val state = UiState(
                    error = throwable.message,
                    isLoading = false
                )
                emit(state)
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, UiState())

    private fun getPhotoIdArg(): String? = savedStateHandle.get<String>(ARG_PHOTO_ID)

    data class UiState(
        val photo: PhotoDetails? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private companion object {
        // Should be aligned with key from Navigation Components
        private const val ARG_PHOTO_ID = "photoId"
    }
}
