package com.awscherb.cardkeeper.ui.base

interface BasePresenter<in T : BaseView> {
    fun attachView(view: T)

    fun onViewDestroyed()
}
