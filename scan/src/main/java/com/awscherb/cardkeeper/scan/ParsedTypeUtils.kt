package com.awscherb.cardkeeper.scan

import com.awscherb.cardkeeper.core.toAddressBook
import com.awscherb.cardkeeper.core.toEmail
import com.awscherb.cardkeeper.core.toParsedUri
import com.awscherb.cardkeeper.core.toTel
import com.awscherb.cardkeeper.core.toWifi
import com.awscherb.cardkeeper.types.DriverLicenseType
import com.awscherb.cardkeeper.types.ExtendedTypesHelper
import com.awscherb.cardkeeper.types.ParsedResultType

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