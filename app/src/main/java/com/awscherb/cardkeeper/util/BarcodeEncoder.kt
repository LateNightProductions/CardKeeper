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

    private val writer = MultiFormatWriter()

    private fun createBitmap(
        matrix: BitMatrix,
        darkMode: Boolean,
        paddingPx: Int = 0
    ): Bitmap {
        val bgColor = if (darkMode) DARK_BACKGROUND else TRANSPARENT
        val (left, top, width, height) = matrix.enclosingRectangle
        val fullWidth = width + (2 * paddingPx)
        val fullHeight = height + (2 * paddingPx)
        val bitmap = Bitmap.createBitmap(fullWidth, fullHeight, Bitmap.Config.ARGB_8888)
        for (x in 0 until fullWidth) {
            for (y in 0 until fullHeight) {
                when {
                    x < paddingPx || x > (width + paddingPx) || y < paddingPx || y > (height + paddingPx) -> {
                        bitmap.setPixel(x, y, bgColor)
                    }

                    else -> bitmap.setPixel(
                        x,
                        y,
                        if (matrix.get(
                                x + (left - paddingPx),
                                y + (top - paddingPx)
                            )
                        ) BLACK else bgColor
                    )
                }
            }
        }
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
            writer.encode(contents, format, width, height)
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
        paddingPx: Int = 0
    ): Bitmap {
        return createBitmap(
            matrix = encode(
                contents = contents,
                format = format,
                width = width,
                height = height
            ),
            darkMode = inDarkMode,
            paddingPx = paddingPx
        )
    }

    private const val TRANSPARENT = (0x00FFFFFF).toInt()
    private const val BLACK = (0xFF000000).toInt()
    private const val DARK_BACKGROUND = (0xddFFFFFF).toInt()
}
