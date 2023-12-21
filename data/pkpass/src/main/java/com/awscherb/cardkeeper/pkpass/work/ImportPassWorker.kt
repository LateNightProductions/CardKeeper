package com.awscherb.cardkeeper.pkpass.work

import android.content.Context
import android.net.Uri
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.awscherb.cardkeeper.pkpass.db.PkPassDao
import com.google.gson.Gson
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

class ImportPassWorker(
    gson: Gson,
    private val workerParams: WorkerParameters,
    private val context: Context,
    private val pkPassDao: PkPassDao,
) : InputStreamWorker(
    workerParams, context, gson
) {

    companion object {
        const val INPUT_TYPE = "input_type"
        const val TYPE_URI = "type_uri"
        const val TYPE_FILE = "type_file"
        const val URI = "uri"

        const val KEY_PASS_ID = "pass_id"

    }

    override suspend fun doWork(): Result {
        val uriString = workerParams.inputData.getString(URI) ?: throw IllegalArgumentException(
            "URI string is required"
        )
        var inputStream: InputStream? = null
        if (workerParams.inputData.getString(INPUT_TYPE) == TYPE_URI) {
            try {
                context.contentResolver.openInputStream(Uri.parse(uriString))?.let {
                    inputStream = it
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return Result.failure()
            }
        } else {
            uriString.let {
                try {
                    inputStream = FileInputStream(it)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    return Result.failure()
                }
            }
        }

        val passId = inputStream?.use {
            createPassFromZipInput(it)?.let { pass ->
                pkPassDao.insertPass(pass)
                pass.id
            }
        }

        return if (passId == null) {
            Result.failure()
        } else {
            Result.success(
                workDataOf(KEY_PASS_ID to passId)
            )
        }
    }

}