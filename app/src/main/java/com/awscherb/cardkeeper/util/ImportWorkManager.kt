package com.awscherb.cardkeeper.util

import android.content.Context
import android.net.Uri
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.awscherb.cardkeeper.pkpass.work.ImportPassWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImportWorkManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun enqueueUris(uris: List<Uri>): Pair<String, Flow<List<WorkInfo>>> {
        val sessionTag = UUID.randomUUID().toString()
        val requests = uris.map { uri ->
            OneTimeWorkRequestBuilder<ImportPassWorker>()
                .addTag(sessionTag)
                .setInputData(
                    workDataOf(
                        ImportPassWorker.INPUT_TYPE to ImportPassWorker.TYPE_URI,
                        ImportPassWorker.URI to uri.toString()
                    )
                )
                .build()
        }

        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(requests)
        return sessionTag to workManager.getWorkInfosByTagFlow(sessionTag)
    }
}
