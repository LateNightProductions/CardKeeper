package com.awscherb.cardkeeper.ui.base

import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * BasePresenterTest.kt
 * Tablelist
 *
 * Created by Alex Scherb on 8/1/17.
 * Copyright (c) 2017 Tablelist, Inc. All rights reserved
 */
abstract class BasePresenterTest<V : BaseView, P : Presenter<V>> {

    open lateinit var view: V
    lateinit var presenter: P

    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)
        view = Mockito.mock(getViewClass())
        presenter = createPresenter().apply { attachView(provideView()) }
    }

    fun provideView() = view
    abstract fun getViewClass(): Class<V>
    abstract fun createPresenter(): P

}