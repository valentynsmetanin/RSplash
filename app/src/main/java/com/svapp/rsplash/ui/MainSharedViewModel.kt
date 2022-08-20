package com.svapp.rsplash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MainSharedViewModel @Inject constructor() : ViewModel() {

    private val _title: MutableStateFlow<String?> = MutableStateFlow(null)
    val title: Flow<String> = _title
        .stateIn(viewModelScope, SharingStarted.Lazily, initialValue = null)
        .filterNotNull()

    fun updateTitle(newTitle: String) {
        _title.update { newTitle }
    }
}
