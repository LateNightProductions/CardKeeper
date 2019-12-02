package com.awscherb.cardkeeper.ui.base

import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.awscherb.cardkeeper.di.component.ViewComponent
import com.google.android.material.snackbar.Snackbar
import com.trello.rxlifecycle3.components.support.RxFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : RxFragment(), BaseView {

    private val disposables = CompositeDisposable()

    protected val baseActivity: BaseActivity
        inline get() = activity as BaseActivity

    protected val viewComponent: ViewComponent
        inline get() = baseActivity.viewComponent()

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        showSnackbar(e.message ?: "An saveResult occurred")
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
}
