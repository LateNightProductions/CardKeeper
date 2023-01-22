package com.awscherb.cardkeeper.ui.base

import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.awscherb.cardkeeper.di.component.ViewComponent
import com.awscherb.cardkeeper.util.extensions.addLifecycleTextChangedListener
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment(), BaseView {

    override fun onError(e: Throwable) {
        e.printStackTrace()
        showSnackbar(e.message ?: "An error occurred")
    }

    fun showSnackbar(message: Int) = showSnackbar(getString(message))

    fun showSnackbar(message: String) {
        view?.let {
            Snackbar.make(
                it,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    fun dismissKeyboard() {
        activity?.let {
            val imm =
                it.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            it.currentFocus?.let {
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }

    fun EditText.addLifecycleTextWatcher(onTextChanged: (String) -> Unit) {
        this.addLifecycleTextChangedListener(this@BaseFragment, onTextChanged)
    }

    fun EditText.requestFocusAndShowKeyboard() {
        requestFocus()
        activity?.let {
            val imm =
                it.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            it.currentFocus?.let {
                imm.showSoftInput(this, 0)
            }
        }
    }
}
