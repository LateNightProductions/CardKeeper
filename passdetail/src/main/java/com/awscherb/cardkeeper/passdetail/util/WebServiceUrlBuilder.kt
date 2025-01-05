package com.awscherb.cardkeeper.passdetail.util

import com.awscherb.cardkeeper.passdetail.model.PassDetailModel

object WebServiceUrlBuilder {

    fun buildUrl(
        pass: PassDetailModel
    ): String {
        if (!pass.canBeUpdated) return ""
        val cleanedUrl =
            if (pass.webServiceUrl!!.endsWith("/"))
                pass.webServiceUrl.removeSuffix("/")
            else pass.webServiceUrl

        return "$cleanedUrl/v1/passes/${pass.identifier}/${pass.id}"
    }
}