package com.awscherb.cardkeeper.util

import android.content.Context
import android.net.Uri
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.awscherb.cardkeeper.pkpass.work.ImportPassWorker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImportWorkManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun enqueueContentUri(uri: Uri): Flow<WorkInfo> {
        val req = OneTimeWorkRequestBuilder<ImportPassWorker>()
            .setInputData(
                workDataOf(
                    ImportPassWorker.INPUT_TYPE to ImportPassWorker.TYPE_URI,
                    ImportPassWorker.URI to uri.toString()
                )
            )
            .build()

        with(WorkManager.getInstance(context)) {
            enqueue(req)

            return getWorkInfoByIdFlow(req.id)


        }
    }
}