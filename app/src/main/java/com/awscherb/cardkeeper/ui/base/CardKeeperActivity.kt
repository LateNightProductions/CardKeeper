package com.awscherb.cardkeeper.ui.base

import android.content.ContentResolver
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.dao.PkPassDao
import com.awscherb.cardkeeper.data.work.ImportPassWorker
import dagger.android.AndroidInjection
import javax.inject.Inject

class CardKeeperActivity : AppCompatActivity() {

    @Inject
    lateinit var pkPassDao: PkPassDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_card_keeper)

        val uri = intent.data
        val scheme = uri?.scheme

        if (scheme != null) {
            val type: String
            val passUri: String
            when (ContentResolver.SCHEME_CONTENT == scheme) {
                true -> {
                    type = ImportPassWorker.TYPE_URI
                    passUri = uri.toString()
                }

                false -> {
                    type = ImportPassWorker.TYPE_FILE
                    passUri = uri.encodedPath ?: ""
                }

            }

            val req = OneTimeWorkRequestBuilder<ImportPassWorker>()
                .setInputData(
                    workDataOf(
                        ImportPassWorker.INPUT_TYPE to type,
                        ImportPassWorker.URI to passUri
                    )
                )
                .build()

            WorkManager.getInstance(this)
                .enqueue(req)
        }

    }

}