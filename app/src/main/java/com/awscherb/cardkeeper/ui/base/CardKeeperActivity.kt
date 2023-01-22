package com.awscherb.cardkeeper.ui.base

import android.content.ContentResolver
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.dao.PkPassDao
import com.awscherb.cardkeeper.data.entity.PkPassEntity
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import javax.inject.Inject

class CardKeeperActivity : BaseActivity() {

    @Inject
    lateinit var pkPassDao: PkPassDao

    private val gson by lazy { GsonBuilder().create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewComponent().inject(this)
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

    fun unzip(stream: InputStream?, parent: String) {
        dirChecker(parent, "")
        val buffer = ByteArray(1024)
        try {
            val zin = ZipInputStream(stream)
            var ze: ZipEntry?
            while (zin.nextEntry.also { ze = it } != null) {

                Log.v("CardKeeper", "Unzipping " + ze!!.name)
                if (ze!!.isDirectory) {
                    dirChecker(parent, ze!!.name)
                } else {
                    if (ze!!.name.contains("/")) {
                        // lproj dir, should make this more robust
                        val names = ze!!.name.split("/")
                        dirChecker(parent, names[0])
                    }

                    val f = File(parent, ze!!.name)
                    if (!f.exists()) {
                        val success = f.createNewFile()
                        if (!success) {
                            Log.w("CardKeeper", "Failed to create file " + f.name)
                            continue
                        }
                        val fout = FileOutputStream(f)
                        var count: Int
                        while (zin.read(buffer).also { count = it } != -1) {
                            fout.write(buffer, 0, count)
                        }
                        zin.closeEntry()
                        fout.close()
                    }
                }
            }
            zin.close()
        } catch (e: Exception) {
            Log.e("CardKeeper", "unzip", e)
        }
    }

    private fun dirChecker(parent: String, child: String) {
        val f = File(parent, child)
        if (!f.isDirectory) {
            val success = f.mkdirs()
            if (!success) {
                Log.w("CardKeeper", "Failed to create folder " + f.name)
            }
        }
    }

    private fun handleZipInput(input: InputStream) {
        unzip(input, filesDir.absolutePath + "/current")

        val passFile = File(filesDir.absolutePath + "/current/pass.json")
        val logoFile = File(filesDir.absolutePath + "/current/logo.png")
        val pass: PkPassEntity?

        try {
            val fis = FileInputStream(passFile)

            // parse pass
            val reader = BufferedReader(InputStreamReader(fis))
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append("\n")
            }
            reader.close()
            pass = gson.fromJson(sb.toString(), PkPassEntity::class.java)
            pass.id = pass.serialNumber

            // move and rename image
            if (logoFile.exists()) {
                val newOutputFile = File(filesDir.absolutePath + "/${pass.serialNumber}-img")

                val logoInputStream = FileInputStream(logoFile)
                val out = FileOutputStream(newOutputFile)

                val buffer = ByteArray(1024)
                var read: Int
                while (logoInputStream.read(buffer).also { read = it } != -1) {
                    out.write(buffer, 0, read)
                }
                logoInputStream.close()

                // write the output file
                out.flush()
                out.close()

                // delete the original file
                logoFile.delete()
                pass.logoPath = newOutputFile.absolutePath
            }

            lifecycleScope.launch {
                pkPassDao.insertPass(pass)

                deleteDirectory(File(filesDir.absolutePath + "/current"))

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDirectory(directoryToBeDeleted: File): Boolean {
        val allContents = directoryToBeDeleted.listFiles()
        if (allContents != null) {
            for (file in allContents) {
                deleteDirectory(file)
            }
        }
        return directoryToBeDeleted.delete()
    }
}