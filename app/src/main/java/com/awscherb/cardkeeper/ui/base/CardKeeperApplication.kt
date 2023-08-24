package com.awscherb.cardkeeper.ui.base

import android.app.Application
import androidx.work.Configuration
import com.awscherb.cardkeeper.data.api.PkPassApi
import com.awscherb.cardkeeper.data.dao.PkPassDao
import com.awscherb.cardkeeper.data.work.CardKeeperWorkerFactory
import com.awscherb.cardkeeper.di.component.DaggerViewComponent
import com.awscherb.cardkeeper.di.module.AppModule
import com.awscherb.cardkeeper.di.module.DaoModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CardKeeperApplication : Application(), HasAndroidInjector, Configuration.Provider {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var pkPassDao: PkPassDao

    @Inject
    lateinit var pkPassApi: PkPassApi

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerViewComponent.builder()
            .appModule(AppModule(this))
            .daoModule(DaoModule())
            .build()
            .inject(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(
                CardKeeperWorkerFactory(
                    pkPassDao,
                    pkPassApi
                )
            )
            .build()

    }
}
