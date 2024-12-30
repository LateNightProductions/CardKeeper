package com.awscherb.cardkeeper.types

import com.google.zxing.BarcodeFormat

fun BarcodeFormat.isSquare(): Boolean {
    return when (this) {
        BarcodeFormat.DATA_MATRIX,
        BarcodeFormat.QR_CODE,
        BarcodeFormat.AZTEC -> true

        else -> false
    }
}