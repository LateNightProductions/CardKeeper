package com.awscherb.cardkeeper.util.db

import androidx.room.TypeConverter
import com.awscherb.cardkeeper.data.entity.BarcodeStruct
import com.awscherb.cardkeeper.data.entity.PassInfoStruct
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.zxing.BarcodeFormat
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
    fun fromString(string: String): BarcodeFormat = BarcodeFormat.valueOf(string)

    @[TypeConverter JvmStatic]
    fun toString(format: BarcodeFormat): String = format.toString()

    @[TypeConverter JvmStatic]
    fun toBarcodeList(list: String?): List<BarcodeStruct>? {
        val listType = object : TypeToken<List<BarcodeStruct>?>() {
        }.type
        return gson.fromJson(list, listType)
    }

    @[TypeConverter JvmStatic]
    fun fromBarcodeList(list: List<BarcodeStruct>?): String? {
        return gson.toJson(list)
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
    fun toPassInfo(pass: String?): PassInfoStruct? {
        return gson.fromJson(pass, PassInfoStruct::class.java)
    }

    @[TypeConverter JvmStatic]
    fun fromPassInfo(list: PassInfoStruct?): String? {
        return gson.toJson(list)
    }

    @[TypeConverter JvmStatic]
    fun fromDateString(date: String?): Date? =
        date?.let { dateFormat.parse(it) }

    @[TypeConverter JvmStatic]
    fun toDate(date: Date?): String? =
        date?.let { dateFormat.format(it) }


}