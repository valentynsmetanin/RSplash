package com.svapp.rsplash.ui.utils

import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun TextView.afterTextChangedAsFlow(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = doAfterTextChanged { trySend(it) }
        awaitClose { removeTextChangedListener(listener) }
    }
}