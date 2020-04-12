package com.awscherb.cardkeeper.ui.base

import android.app.Application
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.di.component.DaggerViewComponent
import com.awscherb.cardkeeper.di.component.ViewComponent
import com.awscherb.cardkeeper.di.module.AppModule
import com.awscherb.cardkeeper.di.module.DaoModule
import com.awscherb.cardkeeper.di.module.DispatchersModule
import com.clrvynce.android.Clairvoyance


class CardKeeperApplication : Application() {

    lateinit var viewComponent: ViewComponent
        private set

    override fun onCreate() {
        super.onCreate()

        Clairvoyance.init(this, getString(R.string.clrvynceKey))

        viewComponent = DaggerViewComponent.builder()
            .appModule(AppModule(this))
            .daoModule(DaoModule())
            .dispatchersModule(DispatchersModule())
            .build()

    }
}
