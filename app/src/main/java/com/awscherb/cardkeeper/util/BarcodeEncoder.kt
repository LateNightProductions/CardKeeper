package com.awscherb.cardkeeper.util

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

/**
 * Helper class for encoding barcodes as a Bitmap.
 *
 * Adapted from BarcodeEncoder, from the zxing project:
 * https://github.com/zxing/zxing
 *
 * Uses transparent background instead of white
 *
 * Licensed under the Apache License, Version 2.0.
 */
object BarcodeEncoder {

    private fun createBitmap(matrix: BitMatrix, darkMode: Boolean): Bitmap {
        val width = matrix.width
        val height = matrix.height
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] =
                    if (matrix[x, y]) BLACK else if (darkMode) DARK_BACKGROUND else TRANSPARENT
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    @Throws(WriterException::class)
    private fun encode(
        contents: String?,
        format: BarcodeFormat?,
        width: Int,
        height: Int,
    ): BitMatrix {
        return try {
            MultiFormatWriter().encode(contents, format, width, height)
        } catch (e: WriterException) {
            throw e
        } catch (e: Exception) {
            // ZXing sometimes throws an IllegalArgumentException
            throw WriterException(e)
        }
    }


    @Throws(WriterException::class)
    fun encodeBitmap(
        contents: String?,
        format: BarcodeFormat?,
        width: Int,
        height: Int,
        inDarkMode: Boolean,
    ): Bitmap {
        return createBitmap(encode(contents, format, width, height), inDarkMode)
    }

    private const val TRANSPARENT = (0x00FFFFFF).toInt()
    private const val BLACK = (0xFF000000).toInt()
    private const val DARK_BACKGROUND = (0xddFFFFFF).toInt()
}
