package com.awscherb.cardkeeper.data.model

import com.google.zxing.BarcodeFormat

interface PkPassModel : SavedItem {
    val id: String
    val description: String
    val organizationName: String
    val barcode: Barcode?
    val barcodes: List<Barcode>?
    val passTypeIdentifier: String
    val serialNumber: String

    // rgb(0, 187, 82)
    val backgroundColor: String?
    val foregroundColor: String?
    val labelColor: String?

    val boardingPass: BoardingPass?

    val logoPath: String?
}

interface BoardingPass {
    val headerFields: List<FieldObject>?
    val primaryFields: List<FieldObject>?
}

interface Barcode {
    val altText: String?
    val format: String
    val message: String
    val messageEncoding: String
}

data class FieldObject(
    val key: String,
    val label: String,
    val value: String,
)

fun String.toBarcodeFormat() =
    when (this) {
        "PKBarcodeFormatQR" -> BarcodeFormat.QR_CODE
        "PKBarcodeFormatPDF417" -> BarcodeFormat.PDF_417
        "PKBarcodeFormatAztec" -> BarcodeFormat.AZTEC
        "PKBarcodeFormatCode128" -> BarcodeFormat.CODE_128
        else -> throw IllegalArgumentException("Unknown type $this")
    }

fun String?.parseHexColor(): String {
    return when (this) {
        null -> "#000000"
        else -> {
            val parse = subSequence(indexOf("(") + 1, indexOf(")"))
            val numbers = parse.split(",")
            val ints = numbers.map { it.trim().toInt() }
            val hex = ints.map { Integer.toHexString(it) }
            val padded = hex.map { if (it.length == 1) "0$it" else it }
            val sb = StringBuilder()
            padded.forEach { sb.append(it) }
            "#$sb"
        }
    }
}