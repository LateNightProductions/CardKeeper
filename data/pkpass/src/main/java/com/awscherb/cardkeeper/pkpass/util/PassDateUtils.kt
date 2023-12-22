package com.awscherb.cardkeeper.pkpass.util

import java.text.SimpleDateFormat
import java.util.Locale

object PassDateUtils {
    val networkFormat =
        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
        )

    val timezoneFormat =
        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm", Locale.US
        )

    val shortDateFormat = SimpleDateFormat("M/dd/yy", Locale.US)
}