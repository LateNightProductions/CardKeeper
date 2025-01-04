package com.awscherb.cardkeeper.passdetail.worker

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.awscherb.cardkeeper.passdetail.PassDetailModel
import com.awscherb.cardkeeper.passdetail.util.WebServiceUrlBuilder
import com.awscherb.cardkeeper.pkpass.work.UpdatePassWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PassWorkManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun enqueuePassUpdate(pass: PassDetailModel) {
        val req = OneTimeWorkRequestBuilder<UpdatePassWorker>()
            .setInputData(
                workDataOf(
                    UpdatePassWorker.KEY_URL to WebServiceUrlBuilder.buildUrl(pass),
                    UpdatePassWorker.KEY_TOKEN to pass.authenticationToken
                )
            )
            .build()

        WorkManager.getInstance(context)
            .enqueue(req)
    }
}