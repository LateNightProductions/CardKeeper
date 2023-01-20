package com.awscherb.cardkeeper.ui.base

import android.content.ContentResolver
import android.os.Bundle
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.entity.PkPassEntity
import com.google.gson.GsonBuilder
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class CardKeeperActivity : BaseActivity() {

    private val gson by lazy { GsonBuilder().create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_keeper)

        val uri = intent.data
        val scheme = uri?.scheme

        if (ContentResolver.SCHEME_CONTENT == scheme) {
            try {
                contentResolver.openInputStream(uri)?.use {
                    handleZipInput(it)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } else {
            uri?.encodedPath?.let {
                try {
                    val fis = FileInputStream(it)
                    handleZipInput(fis)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun handleZipInput(input: InputStream) {
        try {
            val zis = ZipInputStream(input)
            var entry: ZipEntry
            while (zis.nextEntry.also { entry = it } != null) {
                val filename: String = entry.name
                if (filename == "pass.json") {
                    val s = StringBuilder()
                    var read: Int
                    val buffer = ByteArray(1024)
                    while (zis.read(buffer, 0, 1024).also { read = it } >= 0) s.append(String(buffer, 0, read))
                    val pass = gson.fromJson(s.toString(), PkPassEntity::class.java)
                    println(pass)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}