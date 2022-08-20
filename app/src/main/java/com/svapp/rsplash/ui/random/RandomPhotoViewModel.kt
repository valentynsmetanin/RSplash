package com.svapp.rsplash.ui.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svapp.rsplash.domain.UseCaseResult
import com.svapp.rsplash.domain.model.PhotoDetails
import com.svapp.rsplash.domain.photos.GetRandomPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class RandomPhotoViewModel @Inject constructor(
    private val getRandomPhotoUseCase: GetRandomPhotoUseCase
) : ViewModel() {

    val uiState: Flow<UiState> = flow {
        emit(UiState(isLoading = true))
        val state = when (val result = getRandomPhotoUseCase.invoke(Unit)) {
            is UseCaseResult.Error -> {
                UiState(error = result.exception.message, isLoading = false)
            }
            is UseCaseResult.Success -> {
                UiState(photo = result.data, isLoading = false)
            }
        }
        emit(state)
    }.catch { throwable ->
        UiState(error = throwable.message, isLoading = false).run {
            emit(this)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState())

    data class UiState(
        val photo: PhotoDetails? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}
