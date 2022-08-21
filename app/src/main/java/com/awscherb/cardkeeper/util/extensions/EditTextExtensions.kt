package com.awscherb.cardkeeper.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

fun EditText.addLifecycleTextChangedListener(
    lifecycleOwner: LifecycleOwner,
    onTextChanged: (String) -> Unit
) {
    val tw = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable) {
            onTextChanged(p0.toString())
        }
    }

    lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            addTextChangedListener(tw)
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
            removeTextChangedListener(tw)
        }
    })
}