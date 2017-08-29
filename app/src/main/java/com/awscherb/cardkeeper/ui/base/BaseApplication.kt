package com.awscherb.cardkeeper.ui.base

import android.app.Application

import com.awscherb.cardkeeper.di.component.DaggerViewComponent
import com.awscherb.cardkeeper.di.component.ViewComponent
import com.awscherb.cardkeeper.di.module.AppModule
import com.awscherb.cardkeeper.di.module.DaoModule

class BaseApplication : Application() {

    lateinit var viewComponent: ViewComponent
        private set

    override fun onCreate() {
        super.onCreate()

        viewComponent = DaggerViewComponent.builder()
                .appModule(AppModule(this))
                .daoModule(DaoModule())
                .build()

    }
}
