package com.awscherb.cardkeeper.util

object WebServiceUrlBuilder {

    fun buildUrl(
        webServiceUrl: String,
        identifier: String,
        serial: String
    ): String {
        val cleanedUrl =
            if (webServiceUrl.endsWith("/"))
                webServiceUrl.removeSuffix("/")
            else webServiceUrl

        return "$cleanedUrl/v1/passes/$identifier/$serial"
    }
}