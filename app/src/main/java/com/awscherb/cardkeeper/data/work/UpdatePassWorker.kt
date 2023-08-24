package com.awscherb.cardkeeper.data.work

import android.content.Context
import androidx.work.WorkerParameters
import com.awscherb.cardkeeper.data.api.PkPassApi
import com.awscherb.cardkeeper.data.dao.PkPassDao
import java.io.InputStream

class UpdatePassWorker(
    context: Context,
    private val workerParams: WorkerParameters,
    private val pkPassDao: PkPassDao,
    private val pkPassApi: PkPassApi
) : InputStreamWorker(
    workerParams, context
) {

    companion object {
        const val KEY_URL = "web_service_url"
        const val KEY_TOKEN = "pass_token"
    }

    override suspend fun doWork(): Result {
        val url = workerParams.inputData.getString(KEY_URL) ?: throw IllegalArgumentException()
        val token = workerParams.inputData.getString(KEY_TOKEN) ?: throw IllegalArgumentException()

        val inputStream: InputStream?
        try {
            val response = pkPassApi.getPass(url, "ApplePass $token")

            inputStream = response.body()?.byteStream()
        } catch (e: Exception) {
            return Result.failure()
        }

        inputStream?.let {
            createPassFromZipInput(it)?.let { pass ->
                pkPassDao.updatePass(pass)
            }
        }

        return Result.success()
    }

}