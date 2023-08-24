package com.awscherb.cardkeeper.data.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.awscherb.cardkeeper.data.dao.PkPassDao

class CardKeeperWorkerFactory constructor(
    private val pkPassDao: PkPassDao
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            ImportPassWorker::class.java.name ->
                ImportPassWorker(workerParameters, appContext, pkPassDao)

            else -> null
        }
    }
}