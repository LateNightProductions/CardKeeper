package com.awscherb.cardkeeper.ui.base

import android.app.Application
import androidx.work.Configuration
import com.awscherb.cardkeeper.data.work.CardKeeperWorkerFactory
import com.awscherb.cardkeeper.pkpass.api.PkPassApi
import com.awscherb.cardkeeper.pkpass.db.PkPassDao
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CardKeeperApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var pkPassDao: PkPassDao

    @Inject
    lateinit var pkPassApi: PkPassApi

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(
                CardKeeperWorkerFactory(
                    gson,
                    pkPassDao,
                    pkPassApi
                )
            )
            .build()
}
