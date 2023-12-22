package com.awscherb.cardkeeper.pkpass.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.awscherb.cardkeeper.pkpass.entity.PkPassEntity
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

abstract class InputStreamWorker(
    workerParams: WorkerParameters,
    private val context: Context,
    private val gson: Gson
) : CoroutineWorker(
    context, workerParams
) {

    companion object {
        private const val WORKING_DIR = "/current"

        private const val DEFAULT_TRANSLATION = "en.lproj"
    }

    /**
     * Creates a PkPassEntity from InputStream
     */
    fun createPassFromZipInput(input: InputStream): PkPassEntity? {
        unzipToDirectory(input, context.filesDir.absolutePath + WORKING_DIR)

        var pass: PkPassEntity? = null

        try {
            pass = parsePassEntityFromDisk() ?: return null

            val logo =
                findLargestImageFile("logo") ?: findLargestImageFile("$DEFAULT_TRANSLATION/logo")

            logo?.let { logoFile ->
                attemptImageCopy(
                    imageFile = logoFile,
                    entity = pass,
                    type = "logo",
                ) {
                    pass.logoPath = it
                }
            }

            findLargestImageFile("strip")?.let { stripFile ->
                attemptImageCopy(
                    imageFile = stripFile,
                    entity = pass,
                    type = "strip",
                ) {
                    pass.stripPath = it
                }
            }

            findLargestImageFile("background")?.let { stripFile ->
                attemptImageCopy(
                    imageFile = stripFile,
                    entity = pass,
                    type = "strip",
                ) {
                    pass.backgroundPath = it
                }
            }

            findLargestImageFile("footer")?.let { footerFile ->
                attemptImageCopy(
                    imageFile = footerFile,
                    entity = pass,
                    type = "strip",
                ) {
                    pass.footerPath = it
                }
            }

            findLargestImageFile("thumbnail")?.let { footerFile ->
                attemptImageCopy(
                    imageFile = footerFile,
                    entity = pass,
                    type = "thumbnail",
                ) {
                    pass.thumbnailPath = it
                }
            }

            val translation = parsePassTranslation(DEFAULT_TRANSLATION)
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
        createDirectory(parent, "")
        val buffer = ByteArray(1024)
        try {
            val zin = ZipInputStream(stream)
            var ze: ZipEntry?
            while (zin.nextEntry.also { ze = it } != null) {

                Log.v("CardKeeper", "Unzipping " + ze!!.name)
                if (ze!!.isDirectory) {
                    createDirectory(parent, ze!!.name)
                } else {
                    if (ze!!.name.contains("/")) {
                        // lproj dir, should make this more robust
                        val names = ze!!.name.split("/")
                        createDirectory(parent, names[0])
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
    private fun parsePassEntityFromDisk(): PkPassEntity? {
        var reader: BufferedReader? = null

        return try {
            reader = BufferedReader(
                InputStreamReader(
                    FileInputStream(
                        File(
                            context.filesDir.absolutePath + "$WORKING_DIR/pass.json"
                        )
                    )
                )
            )

            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append("\n")
            }
            val pass = gson.fromJson(sb.toString(), PkPassEntity::class.java)
            // Some serial numbers have slashes, can probably sanitize better here
            // Serial is used for both in-app navigation and image paths
            pass.id = pass.serialNumber.replace("/", "-")
            pass.created = System.currentTimeMillis()
            pass
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            reader?.close()
        }
    }

    private fun parsePassTranslation(
        lang: String,
    ): HashMap<String, String> {
        fun split(str: String): Pair<String, String> {
            val parts = str.split("=").toMutableList()
            if (parts.size < 2) {
                // Probably malformed
                return "" to ""
            } else if (parts.size > 2) {
                // value probably has an `=` in it somewhere, join all parts
                parts[1] = parts.subList(1, parts.size).joinToString { it }
            }
            return parts[0].replace("\"", "").trim() to
                    parts[1].replace("\"", "").trim()
        }

        val out = HashMap<String, String>()
        val languageFile =
            File(context.filesDir.absolutePath + WORKING_DIR + "/$lang", "pass.strings")
        if (languageFile.exists()) {
            // TODO assumes UTF-8, I have seen some UTF-16 encoded strings files, figure that out
            BufferedReader(
                InputStreamReader(
                    FileInputStream(languageFile),
                )
            ).use { br ->
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

    private fun findLargestImageFile(name: String): File? {
        val x3 = File(context.filesDir.absolutePath + "$WORKING_DIR/$name@3x.png")
        val x2 = File(context.filesDir.absolutePath + "$WORKING_DIR/$name@2x.png")
        val x1 = File(context.filesDir.absolutePath + "$WORKING_DIR/$name.png")

        val out = when {
            x3.exists() -> x3
            x2.exists() -> x2
            x1.exists() -> x1
            else -> null
        }

        return out
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

        val cleanedPath = entity.id

        val newOutputFile = File(context.filesDir.absolutePath + "/${cleanedPath}-$type")

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

    private fun createDirectory(parent: String, child: String) {
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