package com.awscherb.cardkeeper.ui.base

import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class Presenter<V : BaseView> : BasePresenter<V> {

    var view: V? = null

    private val disposable = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = view
    }

    @CallSuper
    override fun onViewDestroyed() {
        disposable.clear()
        view = null
    }

    protected fun addDisposable(d: Disposable) {
        disposable.add(d)
    }

}