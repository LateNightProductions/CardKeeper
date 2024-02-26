package com.awscherb.cardkeeper.data.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.awscherb.cardkeeper.pkpass.api.PkPassApi
import com.awscherb.cardkeeper.pkpass.db.PkPassDao
import com.awscherb.cardkeeper.pkpass.work.ImportPassWorker
import com.awscherb.cardkeeper.pkpass.work.UpdatePassWorker
import com.google.gson.Gson

class CardKeeperWorkerFactory(
    private val gson: Gson,
    private val pkPassDao: PkPassDao,
    private val pkPassApi: PkPassApi
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            ImportPassWorker::class.java.name ->
                ImportPassWorker(gson, workerParameters, appContext, pkPassDao)

            UpdatePassWorker::class.java.name ->
                UpdatePassWorker(appContext, gson, workerParameters, pkPassDao, pkPassApi)

            else -> null
        }
    }
}