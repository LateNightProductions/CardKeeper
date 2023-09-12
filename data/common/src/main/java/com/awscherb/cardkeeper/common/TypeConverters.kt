package com.awscherb.cardkeeper.common

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TypeConverters {

    private val gson by lazy { GsonBuilder().create() }

    private val dateFormat by lazy {
        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
        )
    }


    @[TypeConverter JvmStatic]
    fun toTranslation(list: String?): Map<String, String>? {
        val mapType = object : TypeToken<Map<String, String>?>() {
        }.type
        return gson.fromJson(list, mapType)
    }

    @[TypeConverter JvmStatic]
    fun fromTranslation(list: Map<String, String>?): String? {
        return gson.toJson(list)
    }

    @[TypeConverter JvmStatic]
    fun fromDateString(date: String?): Date? =
        date?.let { dateFormat.parse(it) }

    @[TypeConverter JvmStatic]
    fun toDate(date: Date?): String? =
        date?.let { dateFormat.format(it) }


}