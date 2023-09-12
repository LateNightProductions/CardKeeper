package com.awscherb.cardkeeper.pkpass.work

import android.content.Context
import androidx.work.WorkerParameters
import com.awscherb.cardkeeper.pkpass.api.PkPassApi
import com.awscherb.cardkeeper.pkpass.db.PkPassDao
import com.google.gson.Gson

class UpdatePassWorker(
    context: Context,
    gson: Gson,
    private val workerParams: WorkerParameters,
    private val pkPassDao: PkPassDao,
    private val pkPassApi: PkPassApi,
) : InputStreamWorker(
    workerParams, context, gson
) {

    companion object {
        const val KEY_URL = "web_service_url"
        const val KEY_TOKEN = "pass_token"
    }

    override suspend fun doWork(): Result {
        val url = workerParams.inputData.getString(KEY_URL) ?: throw IllegalArgumentException(
            "URL is required"
        )
        val token = workerParams.inputData.getString(KEY_TOKEN) ?: throw IllegalArgumentException(
            "Auth token is required"
        )

        return try {
            val response = pkPassApi.getPass(url, "ApplePass $token")

            val inputStream = response.body()?.byteStream()

            inputStream?.use {
                createPassFromZipInput(it)?.let { pass ->
                    pkPassDao.updatePass(pass)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

    }

}