package com.awscherb.cardkeeper.ui.base

import androidx.annotation.CallSuper
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Named

abstract class Presenter<V : BaseView> constructor(
        private var uiScheduler: Scheduler)
    : BasePresenter<V> {

    var view: V? = null

    private val disposable = CompositeDisposable()

    @CallSuper
    override fun attachView(view: V) {
        this.view = view
    }

    @CallSuper
    override fun onViewDestroyed() {
        disposable.clear()
    }

    protected fun addDisposable(d: Disposable) {
        disposable.add(d)
    }

    fun <T> scheduleFlowable() = FlowableTransformer<T, T> { f -> f.observeOn(uiScheduler) }
    fun <T> scheduleObservable() = ObservableTransformer<T, T> { f -> f.observeOn(uiScheduler) }
    fun <T> scheduleSingle() = SingleTransformer<T, T> { f -> f.observeOn(uiScheduler) }
    fun scheduleCompletable() = CompletableTransformer { f -> f.observeOn(uiScheduler) }
}