package com.awscherb.cardkeeper.ui.base

import com.awscherb.cardkeeper.R
import com.google.android.material.snackbar.Snackbar
import com.trello.rxlifecycle3.components.support.RxFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseFragment : RxFragment() {

    private val disposables = CompositeDisposable()

    protected val baseActivity: BaseActivity
        get() = activity as BaseActivity

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
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
}
