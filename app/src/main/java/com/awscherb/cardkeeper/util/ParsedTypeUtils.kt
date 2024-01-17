package com.awscherb.cardkeeper.util

import com.awscherb.cardkeeper.util.extensions.toAddressBook
import com.awscherb.cardkeeper.util.extensions.toEmail
import com.awscherb.cardkeeper.util.extensions.toParsedUri
import com.awscherb.cardkeeper.util.extensions.toTel
import com.awscherb.cardkeeper.util.extensions.toWifi
import com.google.zxing.client.result.ParsedResultType

object ParsedTypeUtils {

    fun suggestTitle(data: String, type: ParsedResultType): String {
        return when (type) {
            ParsedResultType.ADDRESSBOOK -> data.toAddressBook()?.names?.firstOrNull() ?: ""
            ParsedResultType.WIFI -> data.toWifi()?.ssid ?: ""
            ParsedResultType.TEL -> data.toTel()?.number ?: ""
            ParsedResultType.EMAIL_ADDRESS -> {
                data.toEmail()?.tos?.firstOrNull()?.let { first ->
                    "Email to $first"
                } ?: "Email"
            }
            ParsedResultType.URI -> {
                data.toParsedUri()?.let { parsed ->
                    parsed.title ?: parsed.title
                } ?: "Link"
            }
            ParsedResultType.PRODUCT,
            ParsedResultType.TEXT,
            ParsedResultType.GEO,
            ParsedResultType.SMS,
            ParsedResultType.CALENDAR,
            ParsedResultType.ISBN,
            ParsedResultType.VIN -> ""
        }
    }
}