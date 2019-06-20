package com.awscherb.cardkeeper.ui.base

import androidx.annotation.CallSuper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

abstract class Presenter<V : BaseView> constructor(
    coroutineDispatcher: CoroutineDispatcher
) : BasePresenter<V> {

    private val job = SupervisorJob()

    val uiScope = CoroutineScope(coroutineDispatcher + job)

    lateinit var view: V

    @CallSuper
    override fun attachView(view: V) {
        this.view = view
    }

    @CallSuper
    override fun onViewDestroyed() {
        job.cancel()
    }

    fun uiScope(block: suspend CoroutineScope.() -> Unit) {
        uiScope.launch(block = block)
    }

    fun uiScope(onError: (Throwable) -> Unit, block: suspend CoroutineScope.() -> Unit) {
        uiScope.launch(block = {
            try {
                block()
            } catch (e: Exception) {
                onError(e)
            }
        })
    }
}
