package com.awscherb.cardkeeper.util

import com.awscherb.cardkeeper.util.extensions.toAddressBook
import com.awscherb.cardkeeper.util.extensions.toWifi
import com.google.zxing.client.result.ParsedResultType

object ParsedTypeUtils {

    fun suggestTitle(data: String, type: ParsedResultType): String {
        return when (type) {
            ParsedResultType.ADDRESSBOOK -> data.toAddressBook()?.names?.firstOrNull() ?: ""
            ParsedResultType.WIFI -> data.toWifi()?.ssid ?: ""
            ParsedResultType.EMAIL_ADDRESS,
            ParsedResultType.PRODUCT,
            ParsedResultType.URI,
            ParsedResultType.TEXT,
            ParsedResultType.GEO,
            ParsedResultType.TEL,
            ParsedResultType.SMS,
            ParsedResultType.CALENDAR,
            ParsedResultType.ISBN,
            ParsedResultType.VIN -> ""
        }
    }
}