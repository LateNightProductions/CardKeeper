package com.awscherb.cardkeeper.util.extensions

import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.google.zxing.client.result.AddressBookAUResultParser
import com.google.zxing.client.result.AddressBookDoCoMoResultParser
import com.google.zxing.client.result.AddressBookParsedResult
import com.google.zxing.client.result.BizcardResultParser
import com.google.zxing.client.result.VCardResultParser
import com.google.zxing.client.result.WifiParsedResult
import com.google.zxing.client.result.WifiResultParser

fun String.toAddressBook(): AddressBookParsedResult? {
    val res = emptyResult(this)
    return when {
        this.startsWith("MECARD:") -> {
            AddressBookDoCoMoResultParser().parse(res)
        }
        startsWith("BIZCARD:") -> {
            BizcardResultParser().parse(res)
        }
        else -> {
            VCardResultParser().parse(res)
        }
    }
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