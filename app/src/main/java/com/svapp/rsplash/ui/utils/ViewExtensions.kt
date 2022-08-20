package com.svapp.rsplash.ui.utils

import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun TextView.doAfterTextChanged(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = doAfterTextChanged { trySend(it) }
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}