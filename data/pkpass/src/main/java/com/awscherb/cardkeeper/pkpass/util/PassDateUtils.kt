package com.awscherb.cardkeeper.pkpass.util

import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.Date
import java.util.Locale

object PassDateUtils {

    fun dateStringToLocalTime(date: String): Date {
        return try {
            val odf = OffsetDateTime.parse(date)
            Date(odf.toInstant().toEpochMilli())
        } catch (e: Exception) {
            e.printStackTrace()
            Date()
        }
    }

    val shortDateFormat = SimpleDateFormat("M/dd/yy", Locale.US)
}