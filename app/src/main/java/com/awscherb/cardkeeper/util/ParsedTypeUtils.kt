package com.awscherb.cardkeeper.util

import com.awscherb.cardkeeper.barcode.model.DriverLicenseType
import com.awscherb.cardkeeper.barcode.model.ExtendedTypesHelper
import com.awscherb.cardkeeper.util.extensions.toAddressBook
import com.awscherb.cardkeeper.util.extensions.toEmail
import com.awscherb.cardkeeper.util.extensions.toParsedUri
import com.awscherb.cardkeeper.util.extensions.toTel
import com.awscherb.cardkeeper.util.extensions.toWifi
import com.google.zxing.client.result.ParsedResultType

object ParsedTypeUtils {

    fun suggestTitle(data: String, type: ParsedResultType): String {
        val extendedType = ExtendedTypesHelper.matchType(data)
        return when {
            type == ParsedResultType.ADDRESSBOOK -> data.toAddressBook()?.names?.firstOrNull() ?: ""
            type == ParsedResultType.WIFI -> data.toWifi()?.ssid ?: ""
            type == ParsedResultType.TEL -> data.toTel()?.number ?: ""
            type == ParsedResultType.EMAIL_ADDRESS -> {
                data.toEmail()?.tos?.firstOrNull()?.let { first ->
                    "Email to $first"
                } ?: "Email"
            }

            type == ParsedResultType.URI -> {
                data.toParsedUri()?.let { parsed ->
                    parsed.title ?: parsed.title
                } ?: "Link"
            }

            extendedType is DriverLicenseType ->
                extendedType.getFullName()

            else -> ""
        }
    }
}