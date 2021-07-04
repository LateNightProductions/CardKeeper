package com.awscherb.cardkeeper.ui.create

import android.os.Parcelable
import com.google.zxing.BarcodeFormat
import kotlinx.parcelize.Parcelize


sealed class CreateType(val format: BarcodeFormat, val title: String) {
    @Parcelize
    object Aztec : CreateType(BarcodeFormat.AZTEC, "Aztec"), Parcelable

    @Parcelize
    object Code128 : CreateType(BarcodeFormat.CODE_128, "Code 128"), Parcelable

    @Parcelize
    object DataMatrix : CreateType(BarcodeFormat.DATA_MATRIX, "Data Matrix"), Parcelable

    @Parcelize
    object PDF417 : CreateType(BarcodeFormat.PDF_417, "PDF 417"), Parcelable

    @Parcelize
    object QRCode : CreateType(BarcodeFormat.QR_CODE, "QR Code"), Parcelable

    companion object {
        fun typeForFormat(format: BarcodeFormat): CreateType =
            when (format) {
                BarcodeFormat.AZTEC -> Aztec
                BarcodeFormat.CODE_128 -> Code128
                BarcodeFormat.DATA_MATRIX -> DataMatrix
                BarcodeFormat.PDF_417 -> PDF417
                BarcodeFormat.QR_CODE -> QRCode
                else -> throw IllegalStateException("$format not yet supported")
            }
    }
}
