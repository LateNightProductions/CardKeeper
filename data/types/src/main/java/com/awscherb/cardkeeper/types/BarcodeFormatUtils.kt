package com.awscherb.cardkeeper.types


fun BarcodeFormat.isSquare(): Boolean {
    return when (this) {
        BarcodeFormat.DATA_MATRIX,
        BarcodeFormat.QR_CODE,
        BarcodeFormat.AZTEC -> true

        else -> false
    }
}