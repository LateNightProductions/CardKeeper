package com.awscherb.cardkeeper.scan

import com.google.zxing.BarcodeFormat as ZXingFormat
import com.google.zxing.client.result.ParsedResultType as ZXingParsedResult
import com.awscherb.cardkeeper.types.BarcodeFormat
import com.awscherb.cardkeeper.types.DriverLicenseType
import com.awscherb.cardkeeper.types.ExtendedTypesHelper
import com.awscherb.cardkeeper.types.ParsedResultType


fun ZXingFormat.toBarcodeFormat() = when (this) {
    ZXingFormat.AZTEC -> BarcodeFormat.AZTEC
    ZXingFormat.CODABAR -> BarcodeFormat.CODABAR
    ZXingFormat.CODE_39 -> BarcodeFormat.CODE_39
    ZXingFormat.CODE_93 -> BarcodeFormat.CODE_93
    ZXingFormat.CODE_128 -> BarcodeFormat.CODE_128
    ZXingFormat.DATA_MATRIX -> BarcodeFormat.DATA_MATRIX
    ZXingFormat.EAN_8 -> BarcodeFormat.EAN_8
    ZXingFormat.EAN_13 -> BarcodeFormat.EAN_13
    ZXingFormat.ITF -> BarcodeFormat.ITF
    ZXingFormat.MAXICODE -> BarcodeFormat.MAXICODE
    ZXingFormat.PDF_417 -> BarcodeFormat.PDF_417
    ZXingFormat.QR_CODE -> BarcodeFormat.QR_CODE
    ZXingFormat.RSS_14 -> BarcodeFormat.RSS_14
    ZXingFormat.RSS_EXPANDED -> BarcodeFormat.RSS_EXPANDED
    ZXingFormat.UPC_A -> BarcodeFormat.UPC_A
    ZXingFormat.UPC_E -> BarcodeFormat.UPC_E
    ZXingFormat.UPC_EAN_EXTENSION -> BarcodeFormat.UPC_EAN_EXTENSION
}

fun toResultType(result: ZXingParsedResult, text: String): ParsedResultType {

    val extendedType = ExtendedTypesHelper.matchType(text)
    return when {
        // extended types have to be first
        extendedType is DriverLicenseType -> ParsedResultType.DRIVER_LICENSE

        result == ZXingParsedResult.ADDRESSBOOK -> ParsedResultType.ADDRESSBOOK
        result == ZXingParsedResult.EMAIL_ADDRESS -> ParsedResultType.EMAIL_ADDRESS
        result == ZXingParsedResult.PRODUCT -> ParsedResultType.PRODUCT
        result == ZXingParsedResult.URI -> ParsedResultType.URI
        result == ZXingParsedResult.TEXT -> ParsedResultType.TEXT
        result == ZXingParsedResult.GEO -> ParsedResultType.GEO
        result == ZXingParsedResult.TEL -> ParsedResultType.TEL
        result == ZXingParsedResult.SMS -> ParsedResultType.SMS
        result == ZXingParsedResult.CALENDAR -> ParsedResultType.CALENDAR
        result == ZXingParsedResult.WIFI -> ParsedResultType.WIFI
        result == ZXingParsedResult.ISBN -> ParsedResultType.ISBN
        result == ZXingParsedResult.VIN -> ParsedResultType.VIN
        // default to text
        else -> ParsedResultType.TEXT
    }
}