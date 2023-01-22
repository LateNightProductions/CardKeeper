package com.awscherb.cardkeeper.ui.base

import android.app.Application
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.di.component.DaggerViewComponent
import com.awscherb.cardkeeper.di.component.ViewComponent
import com.awscherb.cardkeeper.di.module.AppModule
import com.awscherb.cardkeeper.di.module.DaoModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CardKeeperApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerViewComponent.builder()
            .appModule(AppModule(this))
            .daoModule(DaoModule())
            .build()
            .inject(this)
    }
}
