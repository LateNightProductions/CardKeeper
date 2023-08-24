package com.awscherb.cardkeeper.data.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.awscherb.cardkeeper.data.entity.PkPassEntity
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

abstract class InputStreamWorker(
    private val workerParams: WorkerParameters,
    private val context: Context,
) : CoroutineWorker(
    context, workerParams
) {

    companion object {
        const val INPUT_TYPE = "input_type"
        const val TYPE_URI = "type_uri"
        const val TYPE_FILE = "type_file"
        const val URI = "uri"

        private val WORKING_DIR = "/current"
    }

    private val gson by lazy { GsonBuilder().create() }


    /**
     * Creates a PkPassEntity from InputStream
     */
    fun createPassFromZipInput(input: InputStream): PkPassEntity? {
        unzipToDirectory(input, context.filesDir.absolutePath + WORKING_DIR)

        var pass: PkPassEntity? = null

        try {
            pass = parsePassEntityFromDisk()

            val logoFile = findLargestImageFile("logo")
            val stripFile = findLargestImageFile("strip")

            attemptImageCopy(
                imageFile = logoFile,
                entity = pass,
                type = "logo",
            ) {
                pass.logoPath = it
            }

            attemptImageCopy(
                imageFile = stripFile,
                entity = pass,
                type = "strip",
            ) {
                pass.stripPath = it
            }

            val translation = parsePassTranslation("en.lproj")
            if (translation.isNotEmpty()) {
                pass.translation = translation
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            deleteDirectory(
                File(
                    context.filesDir.absolutePath + WORKING_DIR
                )
            )

        }

        return pass
    }

    /**
     * Takes an InputStream of a .zip, unzips in parent dir
     */
    private fun unzipToDirectory(stream: InputStream, parent: String) {
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


    /**
     * Parse pass file from working dir
     */
    private fun parsePassEntityFromDisk(): PkPassEntity {
        val fis = FileInputStream(
            File(
                context.filesDir.absolutePath + "${WORKING_DIR}/pass.json"
            )
        )

        // parse pass
        val reader = BufferedReader(InputStreamReader(fis))
        val sb = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            sb.append(line).append("\n")
        }
        reader.close()
        val pass = gson.fromJson(sb.toString(), PkPassEntity::class.java)
        pass.id = pass.serialNumber
        return pass
    }

    private fun parsePassTranslation(lang: String): HashMap<String, String> {
        fun split(str: String): Pair<String, String> {
            val parts = str.split("=")
            if (parts.size != 2) {
                return "" to ""
            }
            return parts[0].replace("\"", "").trim() to
                    parts[1].replace("\"", "").trim()
        }

        val out = HashMap<String, String>()
        val languageFile =
            File(context.filesDir.absolutePath + WORKING_DIR + "/$lang", "pass.strings")
        if (languageFile.exists()) {
            BufferedReader(FileReader(languageFile)).use { br ->
                br.lines().forEach { line ->
                    if (!line.startsWith("/*")) {
                        line.split(";").forEach {
                            if (it.contains("=")) {
                                val pair = split(it)
                                val (key, translation) = pair
                                if (key.isNotEmpty()) {
                                    out[key] = translation
                                }
                            }
                        }
                    }

                }
            }
        }
        return out
    }

    private fun findLargestImageFile(name: String): File {
        val x3 = File(context.filesDir.absolutePath + "${WORKING_DIR}/$name@3x.png")
        val x2 = File(context.filesDir.absolutePath + "${WORKING_DIR}/$name@2x.png")
        val x1 = File(context.filesDir.absolutePath + "${WORKING_DIR}/$name.png")

        return when {
            x3.exists() -> x3
            x2.exists() -> x2
            else -> x1
        }
    }

    private fun attemptImageCopy(
        imageFile: File,
        entity: PkPassEntity,
        type: String,
        pathAction: (String) -> Unit
    ) {
        if (!imageFile.exists()) {
            return
        }
        val newOutputFile = File(context.filesDir.absolutePath + "/${entity.serialNumber}-$type")

        val imageInputStream = FileInputStream(imageFile)
        val out = FileOutputStream(newOutputFile)

        val buffer = ByteArray(1024)
        var read: Int
        while (imageInputStream.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
        imageInputStream.close()

        // write the output file
        out.flush()
        out.close()

        // delete the original file
        pathAction(newOutputFile.absolutePath)
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