package com.awscherb.cardkeeper.core

import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.google.zxing.client.result.AddressBookDoCoMoResultParser
import com.google.zxing.client.result.AddressBookParsedResult
import com.google.zxing.client.result.BizcardResultParser
import com.google.zxing.client.result.EmailAddressParsedResult
import com.google.zxing.client.result.EmailAddressResultParser
import com.google.zxing.client.result.EmailDoCoMoResultParser
import com.google.zxing.client.result.TelParsedResult
import com.google.zxing.client.result.TelResultParser
import com.google.zxing.client.result.URIParsedResult
import com.google.zxing.client.result.URIResultParser
import com.google.zxing.client.result.VCardResultParser
import com.google.zxing.client.result.WifiParsedResult
import com.google.zxing.client.result.WifiResultParser

fun String.toAddressBook(): AddressBookParsedResult? {
    val res = emptyResult(this)
    return when {
        startsWith("MECARD:") -> {
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

fun String.toTel(): TelParsedResult? {
    return TelResultParser().parse(emptyResult(this))
}

fun String.toParsedUri(): URIParsedResult? {
    return URIResultParser().parse(emptyResult(this))
}

fun String.toEmail(): EmailAddressParsedResult? {
    val res = emptyResult(this)
    return when {
        startsWith("MATMSG:") ->
            EmailDoCoMoResultParser().parse(res)

        else ->
            EmailAddressResultParser().parse(res)
    }
}

private fun emptyResult(text: String) = Result(
    text,
    // these aren't used and are unimportant
    byteArrayOf(),
    arrayOf(),
    BarcodeFormat.QR_CODE
)