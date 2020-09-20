package com.awscherb.cardkeeper.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

fun EditText.textChanges(): Flow<String> {
    val out = MutableStateFlow("")

    addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            out.value = text?.toString() ?: ""
        }

    })

    return out
}