package com.awscherb.cardkeeper.util

import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.canBeUpdated

object WebServiceUrlBuilder {

    fun buildUrl(
        pass: PkPassModel
    ): String {
        if (!pass.canBeUpdated()) return ""
        val cleanedUrl =
            if (pass.webServiceURL!!.endsWith("/"))
                pass.webServiceURL!!.removeSuffix("/")
            else pass.webServiceURL

        return "$cleanedUrl/v1/passes/${pass.passTypeIdentifier}/${pass.id}"
    }
}