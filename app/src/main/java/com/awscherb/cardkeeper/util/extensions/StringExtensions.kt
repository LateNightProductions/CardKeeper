package com.awscherb.cardkeeper.util.extensions

import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.google.zxing.client.result.AddressBookParsedResult
import com.google.zxing.client.result.VCardResultParser
import com.google.zxing.client.result.WifiParsedResult
import com.google.zxing.client.result.WifiResultParser

fun String.toAddressBook(): AddressBookParsedResult? {
    return VCardResultParser().parse(
        emptyResult(this)
    )
}

fun String.toWifi(): WifiParsedResult? {
    return WifiResultParser().parse(emptyResult(this))
}

private fun emptyResult(text: String) = Result(
    text,
    // these aren't used and are unimportant
    byteArrayOf(),
    arrayOf(),
    BarcodeFormat.QR_CODE
)